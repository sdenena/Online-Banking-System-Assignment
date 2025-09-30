INSERT INTO mas_role(role_name, admin, version) VALUES ('Admin', true, 0);
INSERT INTO mas_user(first_name, last_name, username, password, email, version) VALUES ('John', 'Doe', 'admin', '{bcrypt}$2a$10$0yMmx72SmRq7buHGf4F2ze83.vBrzbVzET20QmwrDktYnreYZy1kW', null, 0);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

-- Currency
INSERT INTO public.adm_currency (id, created_by_id, created_date, status, updated_by_id, updated_date, version, currency) VALUES (1, null, null, true, null, null, 0, 'USD');
INSERT INTO public.adm_currency (id, created_by_id, created_date, status, updated_by_id, updated_date, version, currency) VALUES (2, null, null, true, null, null, 0, 'KHR');

-- Exchange Rate
INSERT INTO public.adm_exchange_rate (id, default_exchange_rate, exchange_rate, from_currency, method, to_currency, created_by_id, created_date, status, updated_by_id, updated_date, version) VALUES (1, null, 1.00, 'USD', 0, 'USD', null, null, true, null, null, 0);
INSERT INTO public.adm_exchange_rate (id, default_exchange_rate, exchange_rate, from_currency, method, to_currency, created_by_id, created_date, status, updated_by_id, updated_date, version) VALUES (2, null, 1.00, 'KHR', 0, 'KHR', null, null, true, null, null, 0);
INSERT INTO public.adm_exchange_rate (id, default_exchange_rate, exchange_rate, from_currency, method, to_currency, created_by_id, created_date, status, updated_by_id, updated_date, version) VALUES (3, false, 4000.00, 'USD', 1, 'KHR', null, '2025-09-29 16:45:18.640000', true, null, null, 0);
INSERT INTO public.adm_exchange_rate (id, default_exchange_rate, exchange_rate, from_currency, method, to_currency, created_by_id, created_date, status, updated_by_id, updated_date, version) VALUES (4, false, 4000.00, 'KHR', 0, 'USD', null, '2025-09-29 16:46:56.862000', true, null, null, 0);

CREATE INDEX idx_account_history_from_acc_dir ON account_history(from_account_number, direction);
CREATE INDEX idx_account_history_to_acc_dir ON account_history(to_account_number, direction);
CREATE INDEX idx_account_history_tran_id ON account_history(tran_id);
CREATE INDEX idx_account_history_tran_date ON account_history(tran_date DESC);