Feature: Login dan Admin User OrangeHRM

  Background:
    Given user berada di halaman login OrangeHRM
    When user mengisi username "Admin"
    And user mengisi password "admin123"
    And user menekan tombol login
    Then user berhasil masuk ke halaman dashboard

  Scenario: Admin berhasil menambah satu user baru
    When user mengisi username "Admin"
    And user mengisi password "admin123"
    And user menekan tombol login
    Then user berhasil masuk ke halaman dashboard
    When user membuat employee baru melalui menu PIM
    When user membuka menu Admin
    And user menekan tombol Add pada halaman Admin
    And user memilih role "ESS"
    And user memilih employee baru yang sudah dibuat
    And user memilih status "Enabled"
    And user mengisi username user baru dengan prefix "user tester01"
    And user mengisi password user baru "Password123!"
    And user menekan tombol Save
    Then user baru berhasil disimpan
