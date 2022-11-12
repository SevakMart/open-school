DELETE c2 FROM category as c2
INNER JOIN category as c1
WHERE
c2.id>c1.id AND
c2.title = c1.title;


ALTER TABLE category
ADD UNIQUE(title);