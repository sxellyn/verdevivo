INSERT INTO users (name, password, email) VALUES
('Maria Oliveira', 'maria123', 'maria@email.com'),
('João Pereira', 'joao456', 'joao@email.com');

-- Plants for Maria (user_id = 1)
INSERT INTO plants (name, species, description, is_watered, user_id) VALUES
('Sunny', 'lavender', 'Smells great and attracts bees.', true, 1),
('Sprout', 'rosemary', 'Aromatic herb used in cooking.', false, 1),
('Bubbles', 'mint', 'Fresh and fast-growing.', true, 1),
('Buddy', 'basil', 'Popular in Italian dishes.', false, 1),
('Fluffy', 'aloe vera', 'Used for skin care and healing.', true, 1);

-- Plants for João (user_id = 2)
INSERT INTO plants (name, species, description, is_watered, user_id) VALUES
('Spikey', 'cactus', 'Needs very little water.', true, 2),
('Shade', 'fern', 'Loves shade and humidity.', false, 2),
('Chubby', 'succulent', 'Stores water in its plump leaves.', true, 2),
('Leafy', 'palm', 'Great for indoor decoration.', false, 2),
('Stripe', 'snake plant', 'Hardy and protective plant.', true, 2);
