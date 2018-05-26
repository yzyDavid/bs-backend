/*
 * script for init test database.
 * test user info:
 * email: me@yuzhenyun.me
 * name: yzy
 * password: 123456
 */

INSERT INTO `user_entity`(email, name, hashed_password, salt) VALUES (
  'me@yuzhenyun.me',
  'yzy',
  'HJFEhLfA5QJH9or9X8jR/X+nbbJSwgM4tkvbLm2Sfyw=',
  'nK+rEsNeBk1MUp2CNZDLrQ=='
);
