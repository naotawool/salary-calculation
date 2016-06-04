package salarycalculation.database.repository;

import salarycalculation.database.OrganizationDao;
import salarycalculation.domain.organization.Organization;
import salarycalculation.domain.organization.OrganizationRepository;
import salarycalculation.entity.OrganizationRecord;

/**
 * 組織リポジトリの実装.
 *
 * @author MASAYUKI
 *
 */
public class OrganizationRepositoryDao implements OrganizationRepository {

    private final OrganizationDao dao;

    public OrganizationRepositoryDao() {
        this.dao = new OrganizationDao();
    }

    @Override
    public Organization find(String id) {
        OrganizationRecord record = dao.get(id);
        return new Organization(record.getCode(), record.getName());
    }

}
