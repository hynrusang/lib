"""
이 프로그램은 주어진 YouTube 채널에서 동영상을 검색하고,
사용자가 설정한 기준 PDW(Per Day Views)를 충족하는 동영상의 제목과 PDW를 출력합니다.
해당 지표를 통해서, 해당 채널이 성장하는데 특히 도움을 준 영상들이 무엇인지 분석할 수 있습니다.

This program searches for videos on a given YouTube channel,
Outputs the title and PDW of the video that meets the user's set criteria for Per Day Views (PDW).
Through this indicator, you can analyze what videos have particularly helped the channel grow.

작성자: 환류상
"""


from googleapiclient.discovery import build
from datetime import datetime
import unicodedata

# Required data for the program
YOUTUBE_API_KEY = "YOUR_GOOGLE_API_KEY"
YOUTUBE_API = build('youtube', 'v3', developerKey=YOUTUBE_API_KEY)
CHANNEL_ID = "TO_ANALYZE_CHANEEL_ID"

# Variable data for the program
masterpiece_classification_point = int(input("How much value do you want to set to meet Per Day Views (PDW)?\n(Recommended:  Average View x Desired channel Growth Rate / 100):") or 0)
masterpiece_list = []
foundation_num = 0
next_page_token = None
today = datetime.now()

def formatting_str(text, max_length=25):
    length = 0
    for i, char in enumerate(text):
        if unicodedata.east_asian_width(char) in "WF": length += 2
        else: length += 1
        if length > max_length - 3: return text[:i] + '...'

    return text + ' ' * (max_length - length)

while True:
    # Overall data about channel from youtube
    all_search_data = YOUTUBE_API.search().list(
        part="id",
        channelId=CHANNEL_ID,
        maxResults=20,
        pageToken=next_page_token,
        order="date"
    ).execute()

    # List of id of videos posted on the channel
    id_list = [item["id"]["videoId"] for item in all_search_data["items"] if item["id"]["kind"] == "youtube#video"]
    if id_list:
        # A list of detailed data extracted from the searched based on id field
        video_list = YOUTUBE_API.videos().list(
            part="snippet,statistics",
            id=",".join(id_list)
        ).execute()

        # Perform masterpiece classification based on detailed data
        for item in video_list["items"]:
            foundation_num += 1
            title = item["snippet"]["title"]
            views=int(item["statistics"]["viewCount"])
            upload_date = datetime.strptime(item["snippet"]["publishedAt"], "%Y-%m-%dT%H:%M:%SZ")
            passed_timestamp = (today - upload_date).days or 1
            
            pdw = views / passed_timestamp
            if masterpiece_classification_point <= pdw: masterpiece_list.append({"title": title, "pdw": pdw})
            
    if int(input(f"found {len(masterpiece_list)} meaningful Video Data out of the Current {foundation_num} Videos.\nShould Keep going? (0: Stop 1: Keep going):") or 1) == 0: break;
    
    next_page_token = all_search_data["nextPageToken"] if "nextPageToken" in all_search_data else None
    if not next_page_token: break

print("\nList of final selected meaningful Videos:")
grade_width = len(masterpiece_list) // 10
for i, item in enumerate(sorted(masterpiece_list, key = lambda a: -a['pdw'])): print(f"{(i+1):>{grade_width}}st:\t{formatting_str(item['title'])}\t:\t{item['pdw']:>10.2f}")
