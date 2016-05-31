package salarycalculation.domain.work;

import java.util.Optional;

/**
 *
 * @author MASAYUKI
 */
public interface WorkRepository {

    Optional<WorkOverTimes> findByEmployeeId(int employeeNo);

}
