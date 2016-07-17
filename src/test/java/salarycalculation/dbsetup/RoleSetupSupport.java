package salarycalculation.dbsetup;

import static com.ninja_squad.dbsetup.Operations.insertInto;

import com.ninja_squad.dbsetup.operation.Operation;

/**
 * DbSetup を使って role テーブルへ事前データを投入するためのサポートクラス。
 *
 * @author naotake
 */
public interface RoleSetupSupport {

    default Operation roleInsert() {
        Operation role = insertInto("role").columns("rank", "amount")
                                           .values("A1", 190000)
                                           .values("A2", 192000)
                                           .values("A3", 195000)
                                           .values("C1", 200000)
                                           .values("C2", 201000)
                                           .values("C3", 202000)
                                           .values("C4", 203000)
                                           .values("C5", 204000)
                                           .values("M1", 300000)
                                           .values("M2", 320000)
                                           .values("M3", 350000)
                                           .values("CE", 500000).build();
        return role;
    }
}
