-- file doc
CREATE TABLE file_doc
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    file_doc_type VARCHAR(31)           NULL,
    is_deleted    BIT(1) DEFAULT 0      NOT NULL,
    _uid          VARCHAR(255)          NULL,
    content       BLOB                  NULL,
    file_format   VARCHAR(255)          NULL,
    file_name     VARCHAR(255)          NULL,
    CONSTRAINT pk_file_doc PRIMARY KEY (id)
);

ALTER TABLE file_doc
    ADD CONSTRAINT uc_64057a60ca511f066af6e8855 UNIQUE (file_name, _uid);

 -- formdoc
CREATE TABLE form_doc
(
    id                        BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted                BIT(1) DEFAULT 0      NOT NULL,
    _uid                      VARCHAR(255)          NULL,
    age                       INT                   NULL,
    height                    INT                   NULL,
    weight                    INT                   NULL,
    diagnoes_health_condition VARCHAR(255)          NULL,
    food_allergies            VARCHAR(255)          NULL,
    waist_circumference       INT                   NULL,
    hip_circumference         INT                   NULL,
    marital_status            INT                   NULL,
    vocation                  VARCHAR(255)          NULL,
    prescribed_drugs          VARCHAR(255)          NULL,
    any_previous_diet_plan    BIT(1)                NULL,
    previous_diet_plan_result VARCHAR(255)          NULL,
    lower_targeted_weight     INT                   NULL,
    upper_targeted_weight     INT                   NULL,
    CONSTRAINT pk_form_doc PRIMARY KEY (id)
);


-- file doc
CREATE TABLE application_file_doc
(
    id           BIGINT           NOT NULL,
    is_mandatory BIT(1) DEFAULT 0 NOT NULL,
    CONSTRAINT pk_application_file_doc PRIMARY KEY (id)
);

ALTER TABLE application_file_doc
    ADD CONSTRAINT FK_APPLICATION_FILE_DOC_ON_ID FOREIGN KEY (id) REFERENCES file_doc (id);

-- medical history
CREATE TABLE medical_history
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    is_deleted  BIT(1) DEFAULT 0      NOT NULL,
    _uid        VARCHAR(255)          NULL,
    type        INT                   NULL,
    save_date   datetime              NULL,
    file_doc_id BIGINT                NULL,
    form_doc_id BIGINT                NULL,
    CONSTRAINT pk_medical_history PRIMARY KEY (id)
);

ALTER TABLE medical_history
    ADD CONSTRAINT FK_MEDICAL_HISTORY_ON_FILE_DOC FOREIGN KEY (file_doc_id) REFERENCES file_doc (id);

ALTER TABLE medical_history
    ADD CONSTRAINT FK_MEDICAL_HISTORY_ON_FORM_DOC FOREIGN KEY (form_doc_id) REFERENCES form_doc (id);