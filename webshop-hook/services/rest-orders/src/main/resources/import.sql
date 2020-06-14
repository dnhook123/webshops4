INSERT INTO Orders(id, orderDate, deliveryTime, shippingAddress, orderReference, orderStatus, totalPrice, customerName, customerEmail)
VALUES (nextval('hibernate_sequence'), '15-07-2020', '15-08-2020',
'wilhelminastraat 6', 'DAIFNAFJ92MD', 'Confirmed', 15.43, 'karel', 'testmail3');
INSERT INTO Orders(id, orderDate, deliveryTime, shippingAddress, orderReference, orderStatus, totalPrice, customerName, customerEmail)
VALUES (nextval('hibernate_sequence'), '15-09-2020', '15-09-2020',
'wilhelminastraat 6', 'DAIFNAFJ92MD', 'OnTheWay', 15.43, 'hendriks', 'testmail2');