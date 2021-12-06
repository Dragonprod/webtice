/* eslint-disable no-unused-vars */
import React from 'react';
import Typography from '@mui/material/Typography';
import styles from './TestPage.module.css';
import Menu from '../../components/referencemenu/ReferenceMenu';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

const TestPage = () => {
  return (
    <div className={styles.mainGrid}>
      <Menu />
      <h2 className={styles.testChoiceTitle}>Выберите тему теста</h2>
      <div className={styles.testsGrid}></div>
    </div>
  );
};
export default TestPage;
