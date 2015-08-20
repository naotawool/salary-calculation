CREATE TABLE IF NOT EXISTS Role(
    rank VARCHAR(2) PRIMARY KEY,
    amount int,
);

CREATE TABLE IF NOT EXISTS Capability(
    rank VARCHAR(2) PRIMARY KEY,
    amount int,
);

CREATE TABLE IF NOT EXISTS Organization(
    code VARCHAR(4) PRIMARY KEY,
    name VARCHAR(255),
)

CREATE TABLE IF NOT EXISTS Employee(
    no INT PRIMARY KEY,
    name VARCHAR(255),
    organization VARCHAR(4) NOT NULL,
    birthday date,
    joinDate date,
    roleRank VARCHAR(2),
    capabilityRank VARCHAR(2),
    commuteAmount int,
    rentAmount int,
    healthInsuranceAmount int,
    employeePensionAmount int,
    incomeTaxAmount int,
    inhabitantTaxAmount int,
    workOverTime1hAmount int,
    PRIMARY KEY(no),
    FOREIGN KEY(roleRank) REFERENCES Role(rank),
    FOREIGN KEY(capabilityRank) REFERENCES Capability(rank),
    FOREIGN KEY(organization) REFERENCES Organization(code)
);

CREATE TABLE IF NOT EXISTS Work(
    employeeNo INT,
    workYearMonth INT,
    workOverTime DECIMAL(3, 1),
    lateNightOverTime DECIMAL(3, 1),
    holidayWorkTime DECIMAL(3, 1),
    holidayLateNightOverTime DECIMAL(3, 1),
    PRIMARY KEY(employeeNo, workYearMonth),
    FOREIGN KEY(employeeNo) REFERENCES Employee(no)
);
