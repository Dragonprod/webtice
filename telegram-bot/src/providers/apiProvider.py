import requests
import logging
from src.core.config import API_BASE_URL

logger = logging.getLogger(__name__)


class API():
    def __init__(self) -> None:
        self.htmlPrefix = '/tag/name'
        self.cssPrefix = '/style/name'
        self.questionsPrefix = '/question'

    def getCssTagInfo(self, css):
        styleInfo = {}

        response = requests.get(f'{API_BASE_URL}{self.cssPrefix}?styleName={css}')

        if response.status_code == 200:
            data = response.json()
            styleInfo['name'] = data['styleName'].replace('<', '[').replace('>', ']')
            styleInfo['description'] = data['description'].replace('<', '[').replace('>', ']')
            styleInfo['syntax'] = data['syntax'].replace('<', '[').replace('>', ']')
        else:
            logger.error("Bad status code. Try again later")

        return styleInfo

    def getHtmlTagInfo(self, tag):
        attributes = []
        tagInfo = {}

        response = requests.get(f'{API_BASE_URL}{self.htmlPrefix}?name={tag}')

        if response.status_code == 200:
            data = response.json()

            for attr in data['attributes']:
                attributes.append(
                    {
                        "attributeName": f'{attr["attributeName"]}',
                        "description": f'{attr["description"]}'
                    }
                )

            tagInfo['name'] = data['name'].replace('<', '').replace('>', '')
            tagInfo['description'] = data['description'].replace('<', '').replace('>', '')
            tagInfo['attributes'] = attributes
        else:
            logger.error("Bad status code. Try again later")

        return tagInfo

    def getQuestions(self):
        response = requests.get(f'{API_BASE_URL}{self.questionsPrefix}')

        if response.status_code == 200:
            data = response.json()
            return data
        else:
            logger.error("Bad status code. Try again later")