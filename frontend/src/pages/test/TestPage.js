/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from 'react';
import styles from './TestPage.module.css';
import Menu from '../../components/referencemenu/ReferenceMenu';
import API from '../../api/api';
import Question from '../../components/question/Question';

const TestPage = () => {
  const [current, setCurrent] = useState(0);
  const [questions, setQuestions] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const getQuestionsData = async () => {
      const questionsResponse = await API.get('/question');
      setQuestions(questionsResponse.data);
      setLoading(false);
    };

    getQuestionsData();
  }, []);

  const handleQuestionChange = () => {
    if (current <= 10)
      setCurrent(prevState => prevState + 1);
  }
  return (
    <>
      {!loading && (
        <div className={styles.mainGrid}>
          <Menu />
          <h2 className={styles.testChoiceTitle}>Выберите тему теста</h2>
          <div className={styles.testsGrid}>
            {
              {
                0: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[0].questionName} answers={questions[0].answers} />,
                1: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[1].questionName} answers={questions[1].answers} />,
                2: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[2].questionName} answers={questions[2].answers} />,
                3: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[3].questionName} answers={questions[3].answers} />,
                4: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[4].questionName} answers={questions[4].answers} />,
                5: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[5].questionName} answers={questions[5].answers} />,
                6: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[6].questionName} answers={questions[6].answers} />,
                7: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[7].questionName} answers={questions[7].answers} />,
                8: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[8].questionName} answers={questions[8].answers} />,
                9: <Question onClick={handleQuestionChange} theme="Тема 1" questionName={questions[9].questionName} answers={questions[9].answers} />,
                10: <h1>Результат: 0/10</h1>
              }[current]
            }

          </div>
        </div>
      )}
    </>
  );
};
export default TestPage;
