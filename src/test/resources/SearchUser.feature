Feature: Cari User OrangeHRM

  Background:
    Given user berada di halaman login OrangeHRM
    When user mengisi username "Admin"
    And user mengisi password "admin123"
    And user menekan tombol login
    Then user berhasil masuk ke halaman dashboard

  Scenario: Admin berhasil mencari user
    When user membuka menu Admin
    And user mengisi username pencarian "Admin"
    And user menekan tombol Search
    Then data user "Admin" ditampilkan
