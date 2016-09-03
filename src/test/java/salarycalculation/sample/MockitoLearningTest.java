package salarycalculation.sample;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

/**
 * Mockito 学習用のテストクラス。
 *
 * @author naotake
 */
public class MockitoLearningTest {

    private MockitoLearning testee;

    @Test
    public void 依存先のオブジェクトについても一括でモック化できること() throws Exception {
        testee = mock(MockitoLearning.class, RETURNS_DEEP_STUBS);

        // 依存先のオブジェクトまで振る舞いを定義
        when(testee.getBar().getBaz().getName()).thenReturn("MOCK!!");

        // 実行
        assertThat(testee.getBar().getBaz().getName(), is("MOCK!!"));

        // 検証
        verify(testee.getBar().getBaz()).getName();
    }

    @Test
    public <MultiMock extends InterfaceFoo & InterfaceBar> void 複数のインタフェースを継承したモックを作成できること()
            throws Exception {

        @SuppressWarnings("unchecked")
        MultiMock testee = (MultiMock) mock(InterfaceFoo.class,
                withSettings().extraInterfaces(InterfaceBar.class));

        // 振る舞いを定義
        when(testee.foo()).thenReturn("Foooo!!");
        when(testee.bar()).thenReturn("Baaaar!!");

        // 実行
        assertThat(testee.foo(), is("Foooo!!"));
        assertThat(testee.bar(), is("Baaaar!!"));

        // 検証
        verify(testee).foo();
        verify(testee).bar();
    }

    private static class MockitoLearning {
        private Bar bar;

        @SuppressWarnings("unused")
        public MockitoLearning() {
            this.bar = new Bar();
        }

        public Bar getBar() {
            return bar;
        }
    }

    private static class Bar {
        private Baz baz;

        public Bar() {
            this.baz = new Baz("MockitoLearning!!");
        }

        public Baz getBaz() {
            return baz;
        }
    }

    private static class Baz {
        private String name;

        public Baz(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static interface InterfaceFoo {
        public String foo();
    }

    private static interface InterfaceBar {
        public String bar();
    }
}
