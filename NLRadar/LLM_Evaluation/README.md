# LLM_Evaluation

**Project Files**:

- This directory contains the following key files:

  1. gpt.py:

     - Script for interacting with GPT

     - Please replace `testpath` with your prompt file path and `api_key` with your own API key.

  2. Example Files (.txt)

     - Text files showcasing chain-of-thought reasoning examples.

     - Example files include:
       - [NLRadar/NLRadar/LLM_Evaluation/com.fab.wflixonline.txt at main · NLRadar/NLRadar](https://github.com/NLRadar/NLRadar/blob/main/NLRadar/LLM_Evaluation/com.fab.wflixonline.txt)
       - [NLRadar/NLRadar/LLM_Evaluation/Orion.Soff.txt at main · NLRadar/NLRadar](https://github.com/NLRadar/NLRadar/blob/main/NLRadar/LLM_Evaluation/Orion.Soff.txt)
       - [NLRadar/NLRadar/LLM_Evaluation/com.peek.all.deleted.messages.recover.txt at main · NLRadar/NLRadar](https://github.com/NLRadar/NLRadar/blob/main/NLRadar/LLM_Evaluation/com.peek.all.deleted.messages.recover.txt)
       - [NLRadar/NLRadar/LLM_Evaluation/fun.ae.funtimes.txt at main · NLRadar/NLRadar](https://github.com/NLRadar/NLRadar/blob/main/NLRadar/LLM_Evaluation/fun.ae.funtimes.txt)
       - [NLRadar/NLRadar/LLM_Evaluation/me.ccrama.redditslide.txt at main · NLRadar/NLRadar](https://github.com/NLRadar/NLRadar/blob/main/NLRadar/LLM_Evaluation/me.ccrama.redditslide.txt)

### Prompting Questions for Assessing NLS Usage Security

- **Inadvertent Exposure:**  Does the app unintentionally expose notification content by transmitting or storing it insecurely, thereby creating potential attack vectors? Consider:
  - Is the notification content transmitted or stored in plaintext rather than encrypted?
  - Are insecure methods used on notification content, such as storing on the device's external storage or transmitting via insecure protocols like HTTP?
  - Note: Inadvertent Exposure should only be considered when both conditions are met simultaneously.
- **Deliberate Harvesting:** Does the app misuse NLS to intentionally collect sensitive information from notifications? Evaluate:
  - Does the app's use of NLS align with its stated or reasonably expected functionalities?
  - Are there filtering processes that specifically target sensitive content (e.g., SMS credentials) or notifications from apps likely to contain sensitive information (e.g., messaging, banking, or email apps)?
  - Note: The presence or absence of sensitive filtering alone is not definitive evidence of deliberate harvesting. Apps may collect all notification content without filtering for malicious purposes, while sensitive filtering could also be used for legitimate functions (e.g., social media message auto-reply apps). Prioritize assessing whether the app's overall intention in using NLS appears benign or malicious.
- **Unwarranted Notification Cancellation:** Does the app exhibit unjustified notification dismissal behaviors? Consider:
  - Is there evidence of the app dismissing notifications to conceal its own malicious activities or other suspicious behavior?
  - Does the app remove notifications from competitor apps in a way that could be considered unfair competition?
  - Note: Unwarranted Notification Cancellation is constituted if one of the conditions met.
- **Notification Interaction with Unsolicited Crafted Content:** Does the app unsafely trigger notification actions (such as replying to social media messages directly via notifications) with unsolicited crafted content? Consider:
  - When interacting with the notification, does the app inject malicious content, such as links to promote scams or malware, or other unsolicited content like advertising or promotional messages?
