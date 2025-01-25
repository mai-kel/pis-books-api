-- Dodanie kategorii
INSERT INTO category (name, description) VALUES
                                             ('Fiction', 'Fictional books'),
                                             ('Science', 'Science-related books'),
                                             ('History', 'Historical books'),
                                             ('Biography', 'Biographies of famous people'),
                                             ('Fantasy', 'Fantasy genre books');

-- Dodanie autorów
INSERT INTO writer (name, last_name) VALUES
                                         ('Adam', 'Smith'),
                                         ('Eve', 'Johnson'),
                                         ('James', 'Brown'),
                                         ('Sophia', 'Davis'),
                                         ('Michael', 'Miller');

-- Dodanie książek
INSERT INTO book (category_id, title, description) VALUES
                                    (1, 'The Fictional World', 'A thrilling adventure in a fictional world'),
                                    (1, 'Mystery Unfolded', 'A mystery novel that keeps you at the edge of your seat'),
                                    (1, 'Parallel Universes', 'A science fiction story about alternate realities'),
                                    (2, 'Introduction to Physics', 'An introductory book on the principles of physics'),
                                    (2, 'Advanced Chemistry', 'An in-depth guide to advanced chemical reactions'),
                                    (2, 'Understanding Quantum Mechanics', 'Exploring the world of quantum mechanics'),
                                    (3, 'History of the Roman Empire', 'A comprehensive history of the Roman Empire'),
                                    (3, 'World Wars', 'The detailed history of the two world wars'),
                                    (3, 'Ancient Civilizations', 'An exploration of ancient civilizations'),
                                    (4, 'Life of Albert Einstein', 'The biography of one of the greatest scientists'),
                                    (4, 'The Story of Shakespeare', 'A biography of the famous playwright William Shakespeare'),
                                    (4, 'Becoming a Leader', 'A biography of famous political leaders'),
                                    (5, 'The Magic Realm', 'A magical fantasy novel'),
                                    (5, 'The Dragons of Dwarven Mountain', 'A fantasy novel with dragons and dwarfs'),
                                    (5, 'The Wizards Apprentice', 'A fantasy novel about a young wizard'),
                                    (1, 'The Unknown Realm', 'A mysterious world beyond comprehension'),
                                    (1, 'The Last Survivor', 'A fictional tale of survival against all odds'),
                                    (2, 'The Nature of Light', 'A scientific exploration of the nature of light'),
                                    (2, 'Physics in Space', 'Physics principles applied in space exploration'),
                                    (2, 'The Evolution of Technology', 'How technology has changed our world'),
                                    (3, 'Medieval History', 'A look into the medieval ages'),
                                    (3, 'The Fall of the Berlin Wall', 'A history of the events surrounding the fall of the Berlin Wall'),
                                    (4, 'Winston Churchill: The British Leader', 'A biography of Winston Churchill'),
                                    (4, 'Marie Curie: A Life of Science', 'Biography of the scientist Marie Curie'),
                                    (4, 'The Life of Nelson Mandela', 'A biography of Nelson Mandela'),
                                    (5, 'The Elven King', 'A story of elves and kings in a magical world'),
                                    (5, 'The Sword of Destiny', 'A fantasy adventure about a magical sword'),
                                    (1, 'The Haunted House', 'A horror-fiction story set in a haunted house'),
                                    (1, 'The Escape Plan', 'A thrilling story of an escape from a high-security prison'),
                                    (2, 'The Secrets of Space', 'A deep dive into the mysteries of space'),
                                    (2, 'Artificial Intelligence', 'A look into the future of AI technology'),
                                    (2, 'The Brain and Mind', 'Understanding how our brain works'),
                                    (3, 'The Age of Exploration', 'A history of the age of exploration'),
                                    (3, 'The Civil Rights Movement', 'A historical account of the civil rights movement'),
                                    (4, 'Steve Jobs: The Innovator', 'A biography of Steve Jobs'),
                                    (4, 'Mahatma Gandhi: Non-Violence in Action', 'A biography of Mahatma Gandhi'),
                                    (5, 'The Warlocks of Stonehenge', 'A fantasy novel involving warlocks and magical stones'),
                                    (5, 'The Lost Kingdom', 'A story of a lost kingdom and its rediscovery'),
                                    (1, 'In the Shadow of the Beast', 'A fictional story about a legendary beast'),
                                    (1, 'The Treasure Hunt', 'A fictional adventure of a treasure hunt'),
                                    (2, 'The Solar System', 'A book about the solar system and planets'),
                                    (2, 'A New Era of Science', 'Exploring the next generation of scientific discoveries'),
                                    (2, 'The Physics of the Universe', 'A detailed look at the universe from a physicists perspective'),
                                    (3, 'The Rise of the Byzantine Empire', 'A history of the Byzantine Empire'),
                                    (3, 'Revolutionary France', 'A historical account of the French Revolution'),
                                    (4, 'Leonardo Da Vinci: Master of Art', 'A biography of Leonardo Da Vinci'),
                                    (4, 'Cleopatra: The Last Pharaoh', 'A biography of Cleopatra'),
                                    (5, 'The Prophecy of the Oracle', 'A fantasy story involving prophecies and oracles'),
                                    (5, 'The Kingdom of the Phoenix', 'A fantasy novel set in a kingdom ruled by phoenixes');

-- Dodanie powiązań Authorship
-- Powiązanie autorów z książkami
INSERT INTO authorship (writer_id, book_id) VALUES
                                                (1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
                                                (1, 6), (2, 7), (3, 8), (4, 9), (5, 10),
                                                (1, 11), (2, 12), (3, 13), (4, 14), (5, 15),
                                                (1, 16), (2, 17), (3, 18), (4, 19), (5, 20),
                                                (1, 21), (2, 22), (3, 23), (4, 24), (5, 25),
                                                (1, 26), (2, 27), (3, 28), (4, 29), (5, 30),
                                                (1, 31), (2, 32), (3, 33), (4, 34), (5, 35),
                                                (1, 36), (2, 37), (3, 38), (4, 39), (5, 40),
                                                (1, 41), (2, 42), (3, 43), (4, 44), (5, 45),
                                                (1, 46), (2, 47), (3, 48), (4, 49), (2, 1);
