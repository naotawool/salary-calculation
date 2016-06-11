package salarycalculation.database.model;

/**
 * 組織を現す Entity。
 *
 * @author naotake
 */
public class OrganizationRecord {

    /** 組織コード */
    private String code;

    /** 組織名 */
    private String name;

    /**
     * 組織コードを取得する。
     *
     * @return 組織コード
     */
    public String getCode() {
        return code;
    }

    /**
     * 組織コードを設定する。
     *
     * @param code 組織コード
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 組織名を取得する。
     *
     * @return 組織名
     */
    public String getName() {
        return name;
    }

    /**
     * 組織名を設定する。
     *
     * @param name 組織名
     */
    public void setName(String name) {
        this.name = name;
    }
}
