package info.louisG;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.junit.jupiter.api.*;

public class AutomaticEmployeeManagementTest 
{
  static Playwright playwright;
  static Browser browser;
  BrowserContext context;
  Page page;

  @BeforeAll
  static void launchBrowser() 
  {
      playwright = Playwright.create();
      browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
              .setHeadless(false));
  }

  @AfterAll
  static void closeBrowser() 
  {
    if (browser != null) 
    {
      browser.close();
    }
    if (playwright != null) 
    {
      playwright.close();
    }
  }

  @BeforeEach
  void createContext() 
  {
    context = browser.newContext();
    page = context.newPage();
    page.navigate("https://d.i3.hr.dmerej.info/");

    // Context: Adding a User and a Team before each test
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
    page.getByPlaceholder("Name").click();
    page.getByPlaceholder("Name").fill("initialUser");
    page.getByPlaceholder("Email").click();
    page.getByPlaceholder("Email").fill("initialUser@gmail.com");
    page.getByPlaceholder("Email").press("Tab");
    page.locator("#id_address_line1").fill("14 rue de la prune");
    page.locator("#id_address_line1").press("Tab");
    page.locator("#id_address_line2").press("Tab");
    page.getByPlaceholder("City").fill("bonville");
    page.getByPlaceholder("City").press("Tab");
    page.getByPlaceholder("Zip code").fill("2003");
    page.getByPlaceholder("Hiring date").press("Tab");
    page.getByPlaceholder("Hiring date").fill("7777-01-12");
    page.getByPlaceholder("Job title").click();
    page.getByPlaceholder("Job title").fill("Jesus");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();

    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
    page.getByPlaceholder("Name").click();
    page.getByPlaceholder("Name").fill("initialTeam");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    // Context///////////////////////////////
  }

  @AfterEach
  void closeContext() 
  {
    page.navigate("https://d.i3.hr.dmerej.info/reset_db");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    if (context != null) 
    {
      context.close();
    }
  }

  // Test for normal behavior //
  @Test
  @DisplayName("Test buttons navigation")
  void buttonTest() 
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Reset database")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).nth(0).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
  }

  @Test
  @DisplayName("Test adding an employee")
  void addingUserTest()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
    page.getByPlaceholder("Name").click();
    page.getByPlaceholder("Name").fill("Louis");
    page.getByPlaceholder("Name").press("Tab");
    page.getByPlaceholder("Email").fill("G");
    page.locator("#id_address_line1").click();
    page.locator("#id_address_line1").fill("1 rue de la pomme");
    page.getByPlaceholder("City").click();
    page.getByPlaceholder("City").fill("bonville");
    page.getByPlaceholder("City").press("Tab");
    page.getByPlaceholder("Zip code").fill("2000");
    page.getByPlaceholder("Hiring date").fill("0006-10-10");
    page.getByPlaceholder("Job title").click();
    page.getByPlaceholder("Job title").fill("Student");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
  }

  @Test
  @DisplayName("Test creating a team")
  void creatingTeamTest()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
    page.getByPlaceholder("Name").click();
    page.getByPlaceholder("Name").fill("myteam");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
  }

  @Test
  @DisplayName("Test adding an employee to a team")
  void addingUserToATeam()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
    page.getByPlaceholder("Name").click();
    page.getByPlaceholder("Name").fill("myteam");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
    page.getByLabel("Team").selectOption(new SelectOption().setLabel("initialTeam"));
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
  }

  @Test
  @DisplayName("Test promoting an employee to manager")
  void promotingUserToManager()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Promote as manager")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
  }

  @Test
  @DisplayName("Test deleting an employee")
  void deletingEmployee()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).nth(0).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
  }

  @Test
  @DisplayName("Test deleting a team")
  void deletingTeam()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Delete")).nth(0).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
  }

  @Test
  @DisplayName("Test viewing team members")
  void viewingTeamMembers()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("View members")).nth(0).click();
  }

  @Test
  @DisplayName("Test resetting the database")
  void resettingDatabase()
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Reset database")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List teams")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  

  // Test bug and tickets //
  @Test
  @DisplayName("Ticket : Impossible to demote a manager")
  void testCannotDemoteManager() 
  {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Promote as manager")).click();
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed")).click();

    // Action : Return to see if we can "demote"
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
      
    // Res : No demote button available
    // page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Demote")).isVisible(); // Should be false
  }

  @Test
  @DisplayName("Hiring date betwenn 0001 and 9999")
  void testAbnormalHiringDate() {
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add new employee")).click();
      page.getByPlaceholder("Name").fill("FutureEmployee");
      page.getByPlaceholder("Email").fill("future@test.com");
      page.getByPlaceholder("Hiring date").fill("9999-12-31");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
      
      // Verify that the employee is well listed despite the absurd date
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
  }

  @Test
  @DisplayName("Twice the same team name (Case sensitive)")
  void testDuplicateTeamCaseSensitive() 
  {
      // Creation of the first team via the setup or manually
      // Team "initialTeam" already exist because of @BeforeEach
      
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Create new team")).click();
      page.getByPlaceholder("Name").fill("INITIALTEAM"); // Même nom mais en majuscules
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
      
      // Test pass if no error occurs and we can see twice the same team name with different cases
  }

  @Test
  @DisplayName("Ticket : Same user in the same team multiple times")
  void testMultipleTimesInSameTeam() 
  {
      // We use the user and team from @BeforeEach
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("List employees")).click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
      
      // Add for the first time
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
      page.getByLabel("Team").selectOption(new SelectOption().setLabel("initialTeam"));
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
      
      // Add a second time (The bug: the application allows it)
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
      page.getByLabel("Team").selectOption(new SelectOption().setLabel("initialTeam"));
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
 }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Page object model usage example
  @Test
  @DisplayName("Example: Add a user to a team multiple times using Page Object Model")
  void testPageModelMultipleTimesInSameTeam() 
  {
      EmployeePage employeePage = new EmployeePage(page);
      
      employeePage.goTo();
      employeePage.editFirstEmployee();
      employeePage.addToTeam("initialTeam");
      
      employeePage.goTo();
      employeePage.editFirstEmployee();
      employeePage.addToTeam("initialTeam");
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
}

///////////////////////////////// Page object model  //////////////////////////////////////

// Définition des sélecteurs
class EmployeePage
{
  private final Page page;
  
  public EmployeePage(Page page) {
    this.page = page;
  }

  public void goTo() 
  {
      page.navigate("https://d.i3.hr.dmerej.info/employees");
  }

  public void editFirstEmployee() 
  {
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Edit")).first().click();
  }

  public void addToTeam(String teamName) 
  {
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add to team")).click();
      page.getByLabel("Team").selectOption(new SelectOption().setLabel(teamName));
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add")).click();
  }


}
//////////////////////////////////////////////////////////////////////////////////////////