# screenshot-automation
A sample to showcase the screenshot automation with Fastlane, Abyssale, Firebase

<img width="1438" alt="Capture d’écran 2021-09-16 à 11 52 26" src="https://user-images.githubusercontent.com/4083164/133786183-4e097d73-e337-4b05-af51-796a03bf5c41.png">

This demo project show how we can automate the full screenshot generation and upload on the Playstore. The CI we use is Github Actions. 
There is two workflow:

# ci_raw_screenshots.yml
- First step : generate raw screenshots with UI tests and [Fastlane](https://docs.fastlane.tools/actions/screengrab/)
- Second step: upload the raw screenshots on Firebase storage
- Third step: return all the screenshots in the artifacts

At this stage a manual check can be done, to be sure the screenshots are OK.

# ci_pretty_screenshots_upload_store.yml
- First step: get image urls from Firebase (public bucket) and construct data in order to configure all screenshots
- Second step: call [Abyssale](https://www.abyssale.com/) API to generate pretty screenshots
- Third step: download the JSON key file in order to be able to push on the Playstore
- Fourth step: push all new screenshots to the Playstore using [Fastlane](http://docs.fastlane.tools/actions/supply/#supply)
