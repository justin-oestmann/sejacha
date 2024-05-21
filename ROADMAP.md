# sejacha Roadmap - Functions-Diagram

## ServerSide

### Classes

- [ ] Users

  - > id (string)
  - > name (string)
  - > email (string)
  - > password (string)
  - > password_changed (timestamp)
  - > state (int)
  - > updated_at (timestamp)
  - > auth (bool)
  - > authKey (string) (only for socket-request-verification)
  - > email_verify_code (string)
  - > email_verified_at (timestamp)
  - > email_verified (bool)

  - [ ] login(email,password)
    - Verify correct email and password and login
  - [ ] login(loginToken)
    - Verify correct loginToken and login
  - [ ] register
    - create new account using set values from class
  - [ ] sendEmailVerificationCode
    - send Code to Email and save it in database for future verification
  - [ ] verifyEmailAddress(code)
    - check if code is set, and code equals the saved code in db

## ClientSide

## Global
