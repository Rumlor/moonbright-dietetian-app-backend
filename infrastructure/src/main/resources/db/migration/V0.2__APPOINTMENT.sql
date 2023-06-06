CREATE TABLE professional_preference
(
    id                          BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted                  BIT(1) DEFAULT 0      NOT NULL,
    _uid                        VARCHAR(255)          NULL,
    session_duration_in_minutes BIGINT                NULL,
    weekends_available           BIT(1) DEFAULT 0      NOT NULL,
    CONSTRAINT pk_professional_preference PRIMARY KEY (id)
);

CREATE TABLE appointment
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted              BIT(1) DEFAULT 0      NOT NULL,
    _uid                    VARCHAR(255)          NULL,
    _pro_uid                VARCHAR(255)          NOT NULL,
    appointmentStatus       INT                   NULL,
    appointment_create_date date                  NOT NULL,
    start                   date                  NULL,
    end                     date                  NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

CREATE TABLE appointment_to_file_docs
(
    appointment_id BIGINT NOT NULL,
    file_doc_id    BIGINT NOT NULL
);

ALTER TABLE appointment_to_file_docs
    ADD CONSTRAINT uc_appointment_to_file_docs_file_doc UNIQUE (file_doc_id);

ALTER TABLE appointment_to_file_docs
    ADD CONSTRAINT fk_apptofildoc_on_appointment FOREIGN KEY (appointment_id) REFERENCES appointment (id);

ALTER TABLE appointment_to_file_docs
    ADD CONSTRAINT fk_apptofildoc_on_file_doc FOREIGN KEY (file_doc_id) REFERENCES file_doc (id);