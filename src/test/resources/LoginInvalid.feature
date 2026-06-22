Feature: Login dengan invalid username

  Scenario: Login gagal dengan credential invalid
    Given user berada di halaman login OrangeHRM
    When user mengisi username "invalid_user"
    And user mengisi password "invalid_password"
    And user menekan tombol login
    Then pesan error login "Invalid credentials" ditampilkan
