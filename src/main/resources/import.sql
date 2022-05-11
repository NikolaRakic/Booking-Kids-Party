SET SQL_SAFE_UPDATES = 0;
DELETE FROM booking_kids_party_db.type_of_service_provider;

INSERT INTO booking_kids_party_db.type_of_service_provider (id, name) VALUES (unhex(replace(uuid(), '-', '')), "Ketering")
INSERT INTO booking_kids_party_db.type_of_service_provider (id, name) VALUES (unhex(replace(uuid(), '-', '')), "Animator")
INSERT INTO booking_kids_party_db.type_of_service_provider (id, name) VALUES (unhex(replace(uuid(), '-', '')), "Igraonica")
