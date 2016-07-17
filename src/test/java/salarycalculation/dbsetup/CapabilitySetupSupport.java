package salarycalculation.dbsetup;

import static com.ninja_squad.dbsetup.Operations.insertInto;

import com.ninja_squad.dbsetup.operation.Operation;

/**
 * DbSetup を使って capability テーブルへ事前データを投入するためのサポートクラス。
 *
 * @author naotake
 */
public interface CapabilitySetupSupport {

    default Operation capabilityInsert() {
        Operation capability = insertInto("capability").columns("rank", "amount")
                                                       .values("AS", 50000)
                                                       .values("PG", 100000)
                                                       .values("SE", 150000)
                                                       .values("PL", 270000)
                                                       .values("PM", 300000).build();
        return capability;
    }
}
