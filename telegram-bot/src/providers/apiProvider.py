import requests
import logging
from src.core.config import API_BASE_URL

logger = logging.getLogger(__name__)


class API():
    def __init__(self) -> None:
        self.prefix = '/tag'
    
    def getTagInfo(self, tag):
        attributes = []
        tagInfo = {}

        response = requests.get(f'{API_BASE_URL}{self.prefix}/{tag}')

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
