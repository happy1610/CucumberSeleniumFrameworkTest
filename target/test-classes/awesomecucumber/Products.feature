Feature: Add To Cart


  Scenario Outline: Products that should not be listed in Products Section
    Given a user in the Home Page within "Products" section
    When search for <Product Name>
    Then the product <Product Name> should not be listed on Product Section

    Examples:
      | Product Name        |
      | PS5                 |
      | PS4                 |
      | PS3                 |


  Scenario Outline: Products that should be listed in Products Section
    Given a user in the Home Page within "Products" section
    When search for <Product Name>
    Then the product <Product Name> should be listed on Product Section

    Examples:
      | Product Name              |
      | Sauce Labs Backpack       |
      | Sauce Labs Fleece Jacket  |
      | Sauce Labs Onesie         |