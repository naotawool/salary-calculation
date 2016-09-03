package salarycalculation.dbsetup;

import static com.ninja_squad.dbsetup.Operations.insertInto;

import com.ninja_squad.dbsetup.operation.Operation;

/**
 * DbSetup を使って organization テーブルへ事前データを投入するためのサポートクラス。
 *
 * @author naotake
 */
public interface OrganizationSetupSupport {

    default Operation organizationInsert() {
        Operation organization = insertInto("organization").columns("code", "name")
                                                           .values("DEV1", "開発部1")
                                                           .values("DEV2", "開発部2")
                                                           .values("DEV3", "開発部3")
                                                           .values("DEV", "開発部").build();
        return organization;
    }
}
