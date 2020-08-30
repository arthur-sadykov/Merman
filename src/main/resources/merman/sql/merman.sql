DROP DATABASE IF EXISTS merman;
CREATE DATABASE IF NOT EXISTS merman;
USE merman;

#
# TABLE STRUCTURE FOR: rights
#

DROP TABLE IF EXISTS rights;

CREATE TABLE rights (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  administrative tinyint(1) DEFAULT NULL,
  editOtherPeopleDocuments tinyint(1) DEFAULT NULL,
  editOtherPeopleDirectoryEntries tinyint(1) DEFAULT NULL,
  printUnsentDocuments tinyint(1) DEFAULT NULL,
  printRegistryAndExportDirectory tinyint(1) DEFAULT NULL,
  numberOfEditingDays int(11) DEFAULT NULL,
  numberOfDaysForAddingDocumentsInTheFuture int(11) DEFAULT NULL,
  groupProcessingOfDirectoriesDocuments tinyint(1) DEFAULT NULL,
  disallowWriteFailedDocument tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY rights_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: users
#

DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  surnameNamePatronymic varchar(255) DEFAULT NULL,
  rights bigint(20) DEFAULT NULL,
  smtpAddress varchar(255) DEFAULT NULL,
  smtpUser varchar(255) DEFAULT NULL,
  smtpPassword varchar(255) DEFAULT NULL,
  smtpHost varchar(255) DEFAULT NULL,
  smtpPort varchar(255) DEFAULT NULL,
  smtpAuthenticationRequired tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_name (name),
  UNIQUE KEY smtp_address (smtpAddress),
  KEY user_rights_idx (rights),
  CONSTRAINT user_rights FOREIGN KEY (rights) REFERENCES rights (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS banks;
CREATE TABLE banks (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  BIK varchar(50) DEFAULT NULL,
  correspondentAccount varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY bank_name (name),
  UNIQUE KEY bik (BIK),
  UNIQUE KEY correspondent_account (correspondentAccount)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS currencies;
CREATE TABLE currencies (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  currencyCode varchar(10) DEFAULT NULL,
  fullName varchar(50) DEFAULT NULL,
  currencyShare varchar(10) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY currencies_name (name),
  UNIQUE KEY currencies_fullName (fullName),
  UNIQUE KEY currencies_code (currencyCode)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;



#
# TABLE STRUCTURE FOR: discounts
#

DROP TABLE IF EXISTS discounts;

CREATE TABLE discounts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  rate decimal(14,3) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY discounts_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: positions
#

DROP TABLE IF EXISTS positions;

CREATE TABLE positions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  minimumPaymentPerDay decimal(14,2) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY positions_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: employees
#

DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  surname varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  patronymic varchar(255) DEFAULT NULL,
  position bigint(20) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  additionalInformation varchar(255) DEFAULT NULL,
  passport varchar(255) DEFAULT NULL,
  birthDate timestamp NULL DEFAULT NULL,
  hireDate timestamp NULL DEFAULT NULL,
  dismissed tinyint(1) DEFAULT NULL,
  driverLicense varchar(255) DEFAULT NULL,
  trustedPerson varchar(255) DEFAULT NULL,
  notShowInStatements tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY positions_phone (phone),
  UNIQUE KEY positions_passport (passport),
  UNIQUE KEY positions_driver_license (driverLicense),
  KEY employees_position_idx (position),
  CONSTRAINT employees_position FOREIGN KEY (position) REFERENCES positions (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: warehouses
#

DROP TABLE IF EXISTS warehouses;

CREATE TABLE warehouses (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  storekeeper bigint(20) DEFAULT NULL,
  physicalAddress varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY warehouse_name (name),
  KEY warehouse_storekeeper_idx (storekeeper),
  CONSTRAINT warehouse_storekeeper FOREIGN KEY (storekeeper) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: firms
#

DROP TABLE IF EXISTS firms;

CREATE TABLE firms (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  fullName varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  physicalAddress varchar(255) DEFAULT NULL,
  legalAddress varchar(255) DEFAULT NULL,
  identifierSED varchar(255) DEFAULT NULL,
  director bigint(20) DEFAULT NULL,
  chiefAccountant bigint(20) DEFAULT NULL,
  certificateOfIndividualEntrepreneur varchar(255) DEFAULT NULL,
  inFaceOf varchar(255) DEFAULT NULL,
  actingOnTheBasisOf varchar(255) DEFAULT NULL,
  additionalInformation varchar(255) DEFAULT NULL,
  inn varchar(20) DEFAULT NULL,
  kpp varchar(10) DEFAULT NULL,
  ogrn varchar(20) DEFAULT NULL,
  okpo varchar(20) DEFAULT NULL,
  okved varchar(50) DEFAULT NULL,
  postcode varchar(50) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  street varchar(255) DEFAULT NULL,
  house varchar(255) DEFAULT NULL,
  housing varchar(255) DEFAULT NULL,
  apartmentsOffice varchar(255) DEFAULT NULL,
  logo longblob DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY firms_name (name),
  UNIQUE KEY firms_fullname (fullName),
  UNIQUE KEY firms_phone (phone),
  UNIQUE KEY firms_email (email),
  UNIQUE KEY firms_physical_address (physicalAddress),
  UNIQUE KEY firms_legal_address (legalAddress),
  UNIQUE KEY firms_identifier_sed(identifierSED),
  UNIQUE KEY firms_inn (inn),
  UNIQUE KEY firms_kpp (kpp),
  UNIQUE KEY firms_ogrn (ogrn),
  UNIQUE KEY firms_okpo (okpo),
  UNIQUE KEY firms_okved (okved),
  KEY firms_director_idx (director),
  KEY firms_chief_accountant_idx (chiefAccountant),
  CONSTRAINT firms_chief_accountant FOREIGN KEY (chiefAccountant) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT firms_director FOREIGN KEY (director) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE

) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;



#
# TABLE STRUCTURE FOR: typesofprices
#

DROP TABLE IF EXISTS typesofprices;

CREATE TABLE typesofprices (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  vatIncluded tinyint(1) DEFAULT NULL,
  currency bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY typesofprices_name (name),
  CONSTRAINT typesofprices_currency  FOREIGN KEY (currency) REFERENCES currencies (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: productsprices
#

DROP TABLE IF EXISTS productsprices;

CREATE TABLE productsprices (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  priceType bigint(20) DEFAULT NULL,
  price decimal(14,2) DEFAULT NULL,
  quantity decimal(14,3) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT productsprices_pricetype  FOREIGN KEY (priceType) REFERENCES typesofprices (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE firmsbankaccounts (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(20),
	bank BIGINT,
	main BOOLEAN,
	basicTable BIGINT,
	PRIMARY KEY (id),
	UNIQUE KEY firmsbankaccounts_name (name),
	CONSTRAINT firmsbankaccounts_basictable  FOREIGN KEY (basicTable) REFERENCES firms (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT firmsbankaccounts_bank  FOREIGN KEY (basicTable) REFERENCES banks (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: contractortypes
#

DROP TABLE IF EXISTS contractortypes;

CREATE TABLE contractortypes (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  isSupplier tinyint(1) DEFAULT NULL,
  isCustomer tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY contractor_type_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4;



#
# TABLE STRUCTURE FOR: contractorscontracts
#

DROP TABLE IF EXISTS contractorscontracts;

CREATE TABLE contractorscontracts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  creditDays int(11) DEFAULT NULL,
  creditAmount decimal(14,2) DEFAULT NULL,
  dateOfContract timestamp NULL DEFAULT NULL,
  dateOfTermination timestamp NULL DEFAULT NULL,
  becauseOf varchar(255) DEFAULT NULL,
  dispatcher bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY contracts_name (name),
  KEY dispatcher_idx (dispatcher),
  CONSTRAINT dispatcher FOREIGN KEY (dispatcher) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: contractors
#

DROP TABLE IF EXISTS contractors;

CREATE TABLE contractors (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  contractorType bigint(20) DEFAULT NULL,
  dispatcher bigint(20) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  priceType bigint(20) DEFAULT NULL,
  discountType bigint(20) DEFAULT NULL,
  formOfPayment bigint(20) DEFAULT NULL,
  firm bigint(20) DEFAULT NULL,
  dealer bigint(20) DEFAULT NULL,
  additionalInformation varchar(1024) DEFAULT NULL,
  fullName varchar(512) DEFAULT NULL,
  legalAddress varchar(255) DEFAULT NULL,
  physicalAddress varchar(255) DEFAULT NULL,
  inn varchar(20) DEFAULT NULL,
  ogrn varchar(20) DEFAULT NULL,
  okpo varchar(20) DEFAULT NULL,
  okved varchar(50) DEFAULT NULL,
  director varchar(255) DEFAULT NULL,
  inFaceOf varchar(255) DEFAULT NULL,
  actingOnTheBasisOf varchar(255) DEFAULT NULL,
  cardNumber varchar(20) DEFAULT NULL,
  passport varchar(255) DEFAULT NULL,
  identifierSED varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY contractors_name (name),
  UNIQUE KEY contractors_phone (phone),
  UNIQUE KEY contractors_email (email),
  UNIQUE KEY contractors_legal_address (legalAddress),
  UNIQUE KEY contractors_physical_address (physicalAddress),
  UNIQUE KEY contractors_inn (inn),
  UNIQUE KEY contractors_ogrn (ogrn),
  UNIQUE KEY contractors_okpo (okpo),
  UNIQUE KEY contractors_okved (okved),
  UNIQUE KEY contractors_card_number (cardNumber),
  UNIQUE KEY contractors_passport (passport),
  UNIQUE KEY contractors_identifier_sed (identifierSED),
  KEY pricetype_idx (priceType),
  KEY discounttype_idx (discountType),
  KEY contractors_dispatcher_idx (dispatcher),
  KEY contractors_firm_idx (firm),
  CONSTRAINT contractors_discount FOREIGN KEY (discountType) REFERENCES discounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT contractors_type FOREIGN KEY (contractorType) REFERENCES contractortypes (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT contractors_dispatcher FOREIGN KEY (dispatcher) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT contractors_firm FOREIGN KEY (firm) REFERENCES firms (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT contractors_price_type FOREIGN KEY (priceType) REFERENCES typesofprices (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: units
#

DROP TABLE IF EXISTS units;

CREATE TABLE units (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  unitCode varchar(10) DEFAULT NULL,
  fullName varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY unit_name (name),
  UNIQUE KEY unit_code (unitCode),
  UNIQUE KEY unit_fullname (fullName)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: productcategories
#

DROP TABLE IF EXISTS productcategories;

CREATE TABLE productcategories (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  disregardWeight tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY productcategories_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: vatrates
#

DROP TABLE IF EXISTS vatrates;

CREATE TABLE vatrates (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  rate decimal(14,3) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY vatrate_name (name)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: flipcontainers
#

DROP TABLE IF EXISTS flipcontainers;

CREATE TABLE flipcontainers (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  fullName varchar(255) DEFAULT NULL,
  shortName varchar(50) DEFAULT NULL,
  unit bigint(20) DEFAULT NULL,
  vendorCode varchar(255) DEFAULT NULL,
  comment varchar(255) DEFAULT NULL,
  price decimal(14,2) DEFAULT NULL,
  depositForOnePiece decimal(14,2) DEFAULT NULL,
  vatRate bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY flipcontainers_name (name),
  UNIQUE KEY flipcontainers_fullname (fullName),
  UNIQUE KEY flipcontainers_vendorcode (vendorCode),
  KEY flipcontainers_unit_idx (unit),
  KEY flipcontainers_vatrate_idx (vatRate),
  CONSTRAINT flipcontainers_unit FOREIGN KEY (unit) REFERENCES units (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT flipcontainers_vatrate FOREIGN KEY (vatRate) REFERENCES vatrates (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;


#
# TABLE STRUCTURE FOR: products
#

DROP TABLE IF EXISTS products;

CREATE TABLE products (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  fullName varchar(255) DEFAULT NULL,
  shortName varchar(50) DEFAULT NULL,
  unit bigint(20) DEFAULT NULL,
  tara bigint(20) DEFAULT NULL,
  unitWeight decimal(14,6) DEFAULT NULL,
  numberOfSeats decimal(14,6) DEFAULT NULL,
  typeOfPackaging varchar(50) DEFAULT NULL,
  numberOfUnits int(11) DEFAULT NULL,
  comment varchar(255) DEFAULT NULL,
  productCategory bigint(20) DEFAULT NULL,
  productType bigint(20) DEFAULT NULL,
  barCode varchar(50) DEFAULT NULL,
  vatRate bigint(20) DEFAULT NULL,
  costPrice decimal(14,2) DEFAULT NULL,
  producer bigint(20) DEFAULT NULL,
  vendorCode varchar(20) DEFAULT NULL,
  additionalInformation varchar(255) DEFAULT NULL,
  shelfLife int(11) DEFAULT NULL,
  warrantyPeriod int(11) DEFAULT NULL,
  storageConditions varchar(512) DEFAULT NULL,
  GOST varchar(255) DEFAULT NULL,
  detailedProductDescription varchar(1024) DEFAULT NULL,
  photo longblob DEFAULT NULL,
  productTypeCode varchar(50) DEFAULT NULL,
  certificate varchar(512) DEFAULT NULL,
  certificateOfStateRegistrationOfProducts varchar(512) DEFAULT NULL,
  batchNumber varchar(255) DEFAULT NULL,
  quantityInABatch int(11) DEFAULT NULL,
  dateOfIssue timestamp NULL DEFAULT NULL,
  certificateScan longblob DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY product_fullname (fullName),
  UNIQUE KEY product_name (name),
  KEY product_unit_idx (unit),
  KEY product_tara_idx (tara),
  KEY product_category_idx (productCategory),
  KEY product_vatrate_idx (vatRate),
  CONSTRAINT product_category FOREIGN KEY (productCategory) REFERENCES productcategories (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT product_tara FOREIGN KEY (tara) REFERENCES flipcontainers (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT product_unit FOREIGN KEY (unit) REFERENCES units (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT product_firm FOREIGN KEY (producer) REFERENCES firms (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT product_vatrate FOREIGN KEY (vatRate) REFERENCES vatrates (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

#
# TABLE STRUCTURE FOR: consumableinvoices
#

DROP TABLE IF EXISTS `consumableinvoices`;
CREATE TABLE `consumableinvoices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` varchar(10) DEFAULT NULL,
  `documentDate` timestamp NULL DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `firm` bigint(20) DEFAULT NULL,
  `warehouse` bigint(20) DEFAULT NULL,
  `contractor` bigint(20) DEFAULT NULL,
  `contract` bigint(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `deliveryTimeFrom` varchar(8) DEFAULT NULL,
  `deliveryTimeTo` varchar(8) DEFAULT NULL,
  `powerOfAttorneyNumber` varchar(10) DEFAULT NULL,
  `powerOfAttorneyDate` timestamp NULL DEFAULT NULL,
  `whoAndToWhomThePowerOfAttorneyWasIssued` varchar(255) DEFAULT NULL,
  `carrierPosition` varchar(255) DEFAULT NULL,
  `consigneePosition` varchar(255) DEFAULT NULL,
  `dispatcher` bigint(20) DEFAULT NULL,
  `director` bigint(20) DEFAULT NULL,
  `chiefAccountant` bigint(20) DEFAULT NULL,
  `checkingAccount` bigint(20) DEFAULT NULL,
  `discount` bigint(20) DEFAULT NULL,
  `typeOfPrices` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY consumableinvoices_number (number),
  CONSTRAINT consumableinvoices_firm FOREIGN KEY (firm) REFERENCES firms (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_warehouse FOREIGN KEY (warehouse) REFERENCES warehouses (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_contract FOREIGN KEY (contract) REFERENCES contractorscontracts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_dispatcher FOREIGN KEY (dispatcher) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_director FOREIGN KEY (director) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_chiefaccountant FOREIGN KEY (chiefAccountant) REFERENCES employees (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_checkingaccount FOREIGN KEY (checkingAccount) REFERENCES FirmsBankAccounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_discount FOREIGN KEY (discount) REFERENCES discounts (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoices_typeofprices FOREIGN KEY (typeOfPrices) REFERENCES typesofprices (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8mb4;




#
# TABLE STRUCTURE FOR: consumableinvoicesproducts
#

DROP TABLE IF EXISTS consumableinvoicesproducts;

CREATE TABLE consumableinvoicesproducts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  product bigint(20) DEFAULT NULL,
  quantity decimal(14,3) DEFAULT NULL,
  price decimal(14,2) DEFAULT NULL,
  amount decimal(14,2) DEFAULT NULL,
  vatRate bigint(20) DEFAULT NULL,
  vatAmount decimal(14,2) DEFAULT NULL,
  flipContainer bigint(20) DEFAULT NULL,
  productRemainder decimal(14,2) DEFAULT NULL,
  baseTable bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT consumableinvoicesproducts_product FOREIGN KEY (product) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoicesproducts_vatrate FOREIGN KEY (vatRate) REFERENCES vatrates (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoicesproducts_flipcontainer FOREIGN KEY (flipContainer) REFERENCES flipcontainers (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT consumableinvoicesproducts_basetable FOREIGN KEY (baseTable) REFERENCES consumableinvoices (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;




#
# TABLE STRUCTURE FOR: typesofcharges
#

DROP TABLE IF EXISTS typesofcharges;

CREATE TABLE typesofcharges (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  position bigint(20) DEFAULT NULL,
  optionOfTheBaseOfCharges int(11) DEFAULT NULL,
  surchargeIsValidFrom timestamp NULL DEFAULT NULL,
  surchargeIsValidUntil timestamp NULL DEFAULT NULL,
  percentageOfSalesAmount decimal(14,3) DEFAULT NULL,
  amountChargedPerUnit decimal(14,2) DEFAULT NULL,
  amountPer1KilogramOfDeliveredProducts decimal(14,2) DEFAULT NULL,
  forOneDeliveryAddress decimal(14,2) DEFAULT NULL,
  forReturningAUnitOfPackaging decimal(14,2) DEFAULT NULL,
  forOneDepartureWithoutProducts decimal(14,2) DEFAULT NULL,
  forDepartureOnTheRoute decimal(14,2) DEFAULT NULL,
  forOneReturnedInvoice decimal(14,2) DEFAULT NULL,
  forCashReception decimal(14,2) DEFAULT NULL,
  changeOfDelivery bigint(20) DEFAULT NULL,
  categoryOfDelivery bigint(20) DEFAULT NULL,
  productCategory bigint(20) DEFAULT NULL,
  startingFromTheNumberOfProducts decimal(14,2) DEFAULT NULL,
  toTheNumberOfProducts decimal(14,2) DEFAULT NULL,
  onlyOnActiveSales tinyint(1) DEFAULT NULL,
  onlyUponReceiptOfMoneyOrReturnOfDocuments tinyint(1) DEFAULT NULL,
  serviceCategory bigint(20) DEFAULT NULL,
  service bigint(20) DEFAULT NULL,
  perUnitOfServiceProvided decimal(14,2) DEFAULT NULL,
  consumptionCategories bigint(20) DEFAULT NULL,
  percentageOfRepaymentAmount decimal(14,3) DEFAULT NULL,
  daysOverdueFrom int(11) DEFAULT NULL,
  daysOverdueUntil int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY typesofcharges_name (name),
  KEY typesofcharges_position_idx (position),
  KEY typesofcharges_product_category_idx (productCategory),
  CONSTRAINT typesofcharges_position FOREIGN KEY (position) REFERENCES positions (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT typesofcharges_product_category FOREIGN KEY (productCategory) REFERENCES productcategories (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;


INSERT INTO rights (id, name, administrative, editOtherPeopleDocuments, editOtherPeopleDirectoryEntries, printUnsentDocuments, printRegistryAndExportDirectory, numberOfEditingDays, numberOfDaysForAddingDocumentsInTheFuture, groupProcessingOfDirectoriesDocuments, disallowWriteFailedDocument) VALUES (1, 'Администратор',0, 0, 1, 1, 0, 128, 139, 1, 1);

INSERT INTO users (id, name, password, surnameNamePatronymic, rights, smtpAddress, smtpUser, smtpPassword, smtpHost, smtpPort, smtpAuthenticationRequired) VALUES (1, 'Гордеев', 'C>"X>rT~}fc', 'Гордеев Андрей Александрович', 1, null, null, null, null, null, 1);

INSERT INTO banks VALUES (1, 'Межрегионбанк', '111111111', '29292929292182914532', 'Нефтекамск');

INSERT INTO currencies VALUES (1, 'руб.' , 643, 'Российский рубль', 'коп');

INSERT INTO discounts VALUES (1, 'Лучший покупатель', 15.00);

INSERT INTO positions VALUES (1, 'Диспетчер', 711.00);

INSERT INTO employees VALUES ('1', 'Щукин', 'Захар', 'Валентинович', '1', '83584565532', NULL, '7030612297', '1998-04-26 00:00:00', '2013-01-05 00:00:00', '1', '6089490514', 'Веселова Инна Андреевна', '0');


INSERT INTO warehouses VALUES (1, 'Основной склад', 1, '452680, Республика Башкортостан, город Нефтекамск, Монтажная, 9', 'kristina90@example.com');

INSERT INTO firms VALUES ('1', 'ОOO Кристальный мир', 'ОOO Кристальный мир', '84567362530', 'example@example.com', '452680, Республика Башкортостан, город Нефтекамск, Монтажная, 9', 'Нефтекамск, пр-т Комсомольский, 44', '456378283947', '1', '1', NULL, NULL, NULL, NULL, '783726352730', '1238436328', '8938283782758', '8234175937', '44.22.88', '12345', 'Нефтекамск', 'пр-т Комсомольский', '44', NULL, '33', NULL);


INSERT INTO typesofprices VALUES (1, 'Лучший клиент', 1, 1);

INSERT INTO productsprices VALUES (1, 1, 132.11, 7);


INSERT INTO firmsbankaccounts VALUES (1, '15979233032910592821', 1, 1, 1);

INSERT INTO contractortypes VALUES (1, 'Покупатель', 0, 1);

INSERT INTO contractorscontracts VALUES (1, '8396605', 87, 4321.75, '2016-08-11', '2019-01-13', null, 1);


INSERT INTO contractors VALUES ('1', 'Андреева Виктория Сергеевна', '1', '1', '89274393150', 'dde@example.com', '1', '1', '1', '1', '1', NULL, NULL, 'Нефтекамск, Сщциалистическая, 123', 'Нефтекамск, Социалистическая, 123', '123456789123', '4567891236540', '1234567891', NULL, '1', NULL, NULL, '1234567', '8345222289', '473829748438');

INSERT INTO units VALUES (1, 'л.', '112', 'литр');
INSERT INTO units VALUES (2, 'шт.', '796', 'штука');

INSERT INTO productcategories VALUES (1, 'Питьевая вода', 1);

INSERT INTO vatrates VALUES (1, '20%', 20.00);
INSERT INTO vatrates VALUES (2, 'Без НДС', 0.00);


INSERT INTO flipcontainers VALUES (1, 'Бутыль 19л', 'Бутыль 19 литров', 'Бут19', 1, null, null, 120, 60, 1);

INSERT INTO products VALUES (1, 'Кристальная', 'Кристальная вода', 'Кристальная', 1, 1, 39.95, 1, NULL, 1, null, 1, NULL, NULL, 1, 290.14, 1, null, null, 1, 1, NULL, NULL, null, NULL, NULL, NULL, NULL, null, null, '2019-12-16', NULL);


INSERT INTO consumableinvoices VALUES (1, '7628966', '2019-03-29', null, 1, 1, 1, 1, null, '15:00:19', '18:12:40', '30099435', '2013-11-22', 'Иммануил Владимирович Сергеев', 'Водитель-экспедитор', 'Водитель-экспедитор', 1, 1, 1, 1, 1, 1);


INSERT INTO consumableinvoicesproducts VALUES (1, 1, 10, 150.00, 1500.00, 1, 300, 1, 1, 1);

INSERT INTO typesofcharges VALUES (1, 'Лучший работник', 1, 1, '2018-11-07', '2021-07-18', 14.27, 443.28, 1513.6, 514.28, 2793.63, 2273.49, 1065.96, 139.95, 1804.09, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1766.31, 1, 7.61, 1, 1);





