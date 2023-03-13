INSERT INTO public.role(nome) VALUES ('ROLE_ADMIN');
INSERT INTO public.role(nome) VALUES ('ROLE_GERENTE');
INSERT INTO public.role(nome) VALUES ('ROLE_COLABORADOR');

INSERT INTO public.usuario(email, senha) VALUES ('admin@usuario.com', '$2a$10$pFrtqxDwOYw0rFq1VQtEx.pBgN6A8tUlVzfPnCs3Aior5NXmMes1m');
INSERT INTO public.usuario(email, senha) VALUES ('gerente@usuario.com', '$2a$10$pFrtqxDwOYw0rFq1VQtEx.pBgN6A8tUlVzfPnCs3Aior5NXmMes1m');
INSERT INTO public.usuario(email, senha) VALUES ('colaborador@usuario.com', '$2a$10$pFrtqxDwOYw0rFq1VQtEx.pBgN6A8tUlVzfPnCs3Aior5NXmMes1m');

INSERT INTO public.usuario_roles(usuario_id, roles_id) VALUES (1, 1);
INSERT INTO public.usuario_roles(usuario_id, roles_id) VALUES (2, 2);
INSERT INTO public.usuario_roles(usuario_id, roles_id) VALUES (3, 3);

INSERT INTO public.endereco(cep, logradouro, numero, bairro, cidade, uf, latitude, longitude) VALUES ('89222-365', 'Rua Piratuba', 100, 'Bom Retiro', 'Joinville', 'SC', '-26.266495', '-48.843587');
INSERT INTO public.endereco(cep, logradouro, numero, bairro, cidade, uf, latitude, longitude) VALUES ('89218-301', 'Rua Carlos Willy Boehm', 100, 'Santo Antônio', 'Joinville', 'SC', '-26.268176', '-48.858760');
INSERT INTO public.endereco(cep, logradouro, numero, bairro, cidade, uf, latitude, longitude) VALUES ('89222-550', 'Rua Otto Benack', 100, 'Bom Retiro', 'Joinville', 'SC', '-26.266376', '-48.843727');

INSERT INTO public.farmacia(razao_social, cnpj, nome_fantasia, email, telefone_celular, endereco_id) VALUES ('Clamed', '99.999.999/9999-99', 'Clamed Bom Retiro', 'clamed@teste.com', '47 99999-9999', 1);
INSERT INTO public.farmacia(razao_social, cnpj, nome_fantasia, email, telefone_celular, endereco_id) VALUES ('Clamed', '99.999.999/9999-98', 'Clamed Sto Antonio', 'clamed@teste.com', '47 99999-9999', 2);
INSERT INTO public.farmacia(razao_social, cnpj, nome_fantasia, email, telefone_celular, endereco_id) VALUES ('Clamed', '99.999.999/9999-97', 'Clamed Bom Retiro 02', 'clamed@teste.com', '47 99999-9999', 3);

INSERT INTO public.medicamento(nome, laboratorio, dosagem, descricao, preco_unitario, tipo) VALUES ('Ibuprofeno', 'Medley', '100mg', 'Este medicamento é indicado para redução da febre e para o alívio de dores.', 10.74, 'Comum');
INSERT INTO public.medicamento(nome, laboratorio, dosagem, descricao, preco_unitario, tipo) VALUES ('Dorflex', 'Sanofi', '300mg', 'Analgésico e relaxante muscular 10 comprimidos.', 6.49, 'Comum');
INSERT INTO public.medicamento(nome, laboratorio, dosagem, descricao, preco_unitario, tipo) VALUES ('Doril', 'Sanofi', '500mg', 'O Doril é um analgésico e anti-inflamatório para tratar as dores de cabeça.', 25.63, 'Comum');
