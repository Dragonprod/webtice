from bs4 import BeautifulSoup
import requests
import re
from typing import Optional


class Answer():
    def __init__(self, text_answer: str, is_right: bool):
        self.text_answer = text_answer
        self.is_right = is_right


class Question():
    def __init__(self, text: str, answers=list[Answer]):
        self.text = text
        self.answers = answers


list_questions = []
html = requests.get("https://yznaika.com/notes/633-web-programming-answers")
soup = BeautifulSoup(html.text, "lxml")
body = soup.find("div", {'class': 'item-page'})
questions = body.find_all('p')
for question in questions:
    question.find("u")
    try:
        splited = re.split(r"\r\n|\n", question.text)
        question_text = splited[0]
        list_answers = []
        for i in range(1, len(splited)):
            is_finded = False
            if splited[i] != "":
                exc = re.search("+", splited[i])
                if exc != None:
                    is_finded = True
                list_answers.append(Answer(splited[i], is_finded))
        question = Question(question_text, list_answers)
        list_questions.append(question)
    except:
        pass
print(len(list_questions))


def handler_question(html_question) -> Optional[Question]:
    try:
        splited = re.split(r"\r\n|\n", html_question.text)
        question_text = re.findall(r". (.*)", splited[0])[0]
        list_answers = []
        for i in range(1, len(splited)):
            is_finded = False
            if splited[i] != "":
                exc = re.search(r"\+", splited[i])
                if exc != None:
                    is_finded = True
                list_answers.append(
                    Answer(re.findall(r"(\+|-) \s*(.*)", splited[i])[0][1], is_finded))
        question = Question(question_text, list_answers)
        list_questions.append(question)
        for i in range(0, len(question.answers)):
            if question.answers[i].is_right == True:
                return question
        return None
    except:
        return None


def main():
    list_questions = []
    html = requests.get(
        "https://yznaika.com/notes/633-web-programming-answers")
    soup = BeautifulSoup(html.text, "lxml")
    body = soup.find("div", {'class': 'item-page'})
    questions = body.find_all('p')
    for question in questions:
        q = handler_question(question)
        if q is not None:
            list_questions.append(q)
    for question in list_questions:
        body = {
            'questionName': question.text,
            'answers': [{"answerName": answer.text_answer, "right": answer.is_right} for answer in question.answers]
        }
        req = requests.post(
            "http://localhost:8080/api/question", json=body)
        print(req.text)

if __name__ == '__main__':
    main()
