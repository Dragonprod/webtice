/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from 'react';
import styles from './TestPage.module.css';
import Menu from '../../components/referencemenu/ReferenceMenu';
import API from '../../api/api';
import Question from '../../components/question/Question';

const TestPage = () => {
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

  return (
    <>
      {!loading && (
        <div className={styles.mainGrid}>
          <Menu />
          <h2 className={styles.testChoiceTitle}>Выберите тему теста</h2>
          <div className={styles.testsGrid}>
            <Question theme="Тема 1" questionName={questions[0].questionName} answers={questions[0].answers} />
          </div>
        </div>
      )}
    </>
  );
};
export default TestPage;
