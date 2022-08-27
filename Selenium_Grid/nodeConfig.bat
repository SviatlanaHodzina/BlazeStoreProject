cd C:\Selenium_Grid
java -Dwebdriver.msedge.driver="msedgedriver.exe" -Dwebdriver.chrome.driver="chromedriver.exe" -Dwebdriver.ie.driver="IEDriverServer.exe" -Dwebdriver.gecko.driver="geckodriver.exe" -jar selenium-server-standalone-4.0.0-alpha-2.jar -role node -nodeConfig nodeconfig.json




