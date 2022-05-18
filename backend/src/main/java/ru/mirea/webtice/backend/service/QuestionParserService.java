package ru.mirea.webtice.backend.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Answer;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.repository.QuestionRepository;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionParserService {

    private final String http = "https://yznaika.com/notes/633-web-programming-answers";

    @Autowired
    private QuestionRepository questionRepository;

    public QuestionParserService() {

    }

    public void parseQuestion(Element question) throws IOException{
        try {
            Question question1 = new Question();
            String[] strings = question.text().split("- |\\+ ");
            Pattern patternQuestion = Pattern.compile("^[0-9]+\\. (.*)$");
            Matcher m = patternQuestion.matcher(strings[0]);
            if (m.find()) {
                question1.setQuestionName(m.group(1).trim());
            }
            else
            {
                return;
            }
            Pattern rightAnswer = Pattern.compile("\\+ (.+)( - )(.*)");
            Matcher rightMatcher = rightAnswer.matcher(question.text());
            String rightAnswerString = "";
            if (rightMatcher.find()) {
                rightAnswerString = rightMatcher.group(0);
                String[] answers = rightAnswerString.split("- |\\+ ");
                rightAnswerString = answers[1].trim();
                System.out.println(rightAnswerString);
            }
            Set<Answer> answerSet = new HashSet<>();
            for (int i = 1; i < strings.length; i++){
                Answer answer = new Answer();
                answer.setAnswerName(strings[i].trim());
                answer.setQuestion(question1);
                if (rightAnswerString.equals(answer.getAnswerName())){
                    answer.setIs_right(true);
                }
                answerSet.add(answer);
            }
            question1.setAnswers(answerSet);
            questionRepository.save(question1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void start() throws IOException {
        Document doc = Jsoup.connect(this.http).get();
        Element body = doc.select("div.item-page").first();
        Elements questions = body.select("p");
        for(Element question: questions){
//            System.out.println(questions);
            parseQuestion(question);
        }
    }
}

