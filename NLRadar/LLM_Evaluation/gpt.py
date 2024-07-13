import os
import requests
import json
from openai import OpenAI
import httpx
import concurrent.futures

def ask_gpt4_content(content):
    client = OpenAI(
        base_url="https://api.xty.app/v1",
        api_key="sk-CyEovzSM39vTtPTD19AfF38fBb97458d81F13fEdB6E6F182",
        http_client=httpx.Client(
            base_url="https://api.xty.app/v1",
            follow_redirects=True,
        ),
    )

    completion = client.chat.completions.create(
        # model="claude-3-5-sonnet-20240620",
        model="gpt-4",
        # model="gpt-4-vision-preview",
        messages=[
            {"role": "user", "content": content},
        ],
        max_tokens=1000
    )
    print(completion.choices[0].message.content)
    return completion.choices[0].message.content

def process_file(testfile, testpath, outputpath):
    if os.path.exists(os.path.join(outputpath, testfile)):
        return
    if os.path.isdir(os.path.join(testpath,testfile)):
        return
    with open(os.path.join(testpath, testfile), "r", encoding='utf-8') as file:
        data = file.read()
        command = data
        result = ask_gpt4_content(command)
        print(f"Processed {testfile}: {result}")
        with open(os.path.join(outputpath, testfile), "w", encoding='utf-8') as f:
            f.write(result)



if __name__ == '__main__':
    testpath = r"your prompt location"
    outputpath = r"your output location"
    testfiles = os.listdir(testpath)

    with concurrent.futures.ThreadPoolExecutor(max_workers=30) as executor:
        futures = [executor.submit(process_file, testfile, testpath, outputpath) for testfile in testfiles]
        for future in concurrent.futures.as_completed(futures):
            try:
                future.result()
            except Exception as e:
                print(f"Error processing file: {e}")
