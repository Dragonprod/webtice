/* eslint-disable no-unused-vars */
import React from 'react';
import styles from './TestChoicePage.module.css';
import Menu from '../../components/referencemenu/ReferenceMenu';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { BrowserRouter as Router, Link } from "react-router-dom";

const TestChoicePage = () => {
  return (
    <div className={styles.mainGrid}>
      <Menu />
      <h2 className={styles.testChoiceTitle}>Выберите тему теста</h2>
      <div className={styles.testsGrid}>
        <Link className={styles.testThemeCard} to="/test/process">
          <h3 className={styles.testTitle}>Тема 1</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </Link>
        <Link className={styles.testThemeCard} to="/test/process">
          <h3 className={styles.testTitle}>Тема 2</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </Link>
        <Link className={styles.testThemeCard} to="/test/process">
          <h3 className={styles.testTitle}>Тема 3</h3>
          <ArrowForwardIosIcon className={styles.nextIcon} />
        </Link>
      </div>
    </div>
  );
};
export default TestChoicePage;
