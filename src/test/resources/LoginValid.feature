Feature: Login dengan valid User OrangeHRM

  Scenario: Login berhasil dengan credential valid
    When user mengisi username "Admin"
    And user mengisi password "admin123"
    And user menekan tombol login
    Then user berhasil masuk ke halaman dashboard
