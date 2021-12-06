/* eslint-disable no-unused-vars */
import React from 'react';
import Typography from '@mui/material/Typography';
import styles from './TestChoicePage.module.css';
import Menu from '../../components/referencemenu/ReferenceMenu';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

const TestChoicePage = () => {
  return (
    <div className={styles.mainGrid}>
      <Menu />
      <h2 className={styles.testChoiceTitle}>Выберите тему теста</h2>
      <div className={styles.testsGrid}>
        <div className={styles.testThemeCard}>
          <h3 className={styles.testTitle}>Тема 1</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </div>
        <div className={styles.testThemeCard}>
          <h3 className={styles.testTitle}>Тема 2</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </div>
        <div className={styles.testThemeCard}>
          <h3 className={styles.testTitle}>Тема 3</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </div>
      </div>
    </div>
  );
};
export default TestChoicePage;
