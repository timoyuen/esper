CREATE TABLE IF NOT EXISTS person(
    id varchar(30) primary key,
    password char(50) not null,
    telephone char(15) not null,
    email char(30) not null,
    low_priority_text_reminder char,   # `1` means when a low priority event like `INFO` is happened,
    								   # user will also receive text message
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS stock_code(
    code char(10) not null,
    name varchar(20) not null,
    create_time timestamp,
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS rule_subscription(
 subscription_id int primary key auto_increment,
 userid varchar(30) not null,
 epl_id int,
 start_time datetime,
 end_time datetime,
 is_subscription_valid char,
 user_args 	BLOB not null,
 priority char,  # INFO. IMPORTANT.
 tag_list varchar(30)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS stock_epl_template(
    epl_id int primary key auto_increment,
    epl_str varchar(200) not null,
    rule_description varchar(200) not null,
    -- rule_args varchar(200) not null, what about the length
    rule_args_description blob not null,
    rule_args_example blob not null,
    create_time timestamp
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS tag(
    tag_id int primary key auto_increment,
    tag_name varchar(30)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS epl_tag(
    tag_id int not null,
    epl_id int not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- NOT DONE
ALTER TABLE epl_tag ADD CONSTRAINT fk_tag_tag_id
FOREIGN KEY (tag_id) REFERENCES tag(tag_id);
ALTER TABLE epl_tag ADD CONSTRAINT fk_tag_epl_id
FOREIGN KEY (epl_id) REFERENCES stock_epl_template(epl_id);
ALTER TABLE rule_subscription ADD CONSTRAINT fk_rule_subscription_epl_id
FOREIGN KEY (epl_id) REFERENCES stock_epl_template(epl_id);
ALTER TABLE rule_subscription ADD CONSTRAINT fk_rule_subscription_userid
FOREIGN KEY (userid) REFERENCES person(id);
