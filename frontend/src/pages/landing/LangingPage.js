/* eslint-disable no-unused-vars */
import React from 'react';
import styles from './LandingPage.module.css';

import handbookIcon from '../../assets/handbookIcon.svg';
import codeIcon from '../../assets/codeIcon.svg';
import examIcon from '../../assets/examIcon.svg';

import codeImg from '../../assets/codeImg.svg';
import examImg from '../../assets/examImg.svg';
import handbookImg from '../../assets/handbookImg.svg';
import projectImg from '../../assets/projectImg.svg';

import Menu from '../../components/menu/Menu.js';

export default function LandingPage() {
  return (
    <>
      <Menu />
      <section id='hero' className={styles.hero}>
        <div className={styles.heroContentContainer}>
          <h1 className={styles.heroTitle}>
            Начни учиться прямо{' '}
            <span className={styles.highlightSpan}>сейчас</span>
          </h1>
          <p className={styles.heroSubtitle}>
            Webtice — образовательная платформа РТУ МИРЭА для обучения студентов
            веб-разработке и проведения экзаменов, преимущественно по
            компьютерным дисциплинам
          </p>
          <div className={styles.heroButtonsContainer}>
            <div className={styles.heroButtonCard}>
              <img
                className={styles.heroButtonIcon}
                src={handbookIcon}
                alt='Иконка справочника'
              />
              <p className={styles.text}>
                Интерактивный справочник по HTML и CSS элементам
              </p>
              <a
                href='/refbook'
                className={`${styles['go-to-btn']} ${styles['go-to-hand-book-btn']}`}>
                Веб-справочник
              </a>
            </div>
            <div className={styles.heroButtonCard}>
              <img
                className={styles.heroButtonIcon}
                src={codeIcon}
                alt='Иконка кода'
              />
              <p className={styles.text}>
                Онлайн задания по веб-разработке (преимущественно верстка)
              </p>
              <a
                href='/test'
                className={`${styles['go-to-btn']} ${styles['go-to-online-coding-btn']}`}>
                Онлайн Тест
              </a>
            </div>
            <div className={styles.heroButtonCard}>
              <img
                className={styles.heroButtonIcon}
                src={examIcon}
                alt='Иконка экзамена'
              />
              <p className={styles.text}>Система проведения экзаменов</p>
              <a
                href='/'
                className={`${styles['go-to-btn']} ${styles['go-to-exam-btn']}`}>
                Экзамен
              </a>
            </div>
          </div>
        </div>
      </section>
      <section id='handbook' className={styles.handbook}>
        <div className={styles.contentContainer}>
          <img
            className={styles.sectionImg}
            src={handbookImg}
            alt='SVG-изображение'
          />
          <div className={styles.textContainer}>
            <h2 className={styles.themeTitle}>Веб-справочник</h2>
            <p className={styles.text}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              nemo obcaecati dolor blanditiis magnam repudiandae in repellendus
              voluptates quibusdam natus?
            </p>
          </div>
        </div>
      </section>
      <section id='coding' className={styles.coding}>
        <div className={styles.contentContainer}>
          <img
            className={styles.sectionImg}
            src={codeImg}
            alt='SVG-изображение'
          />
          <div className={styles.textContainer}>
            <h2 className={styles.themeTitle}>Онлайн кодинг</h2>
            <p className={styles.text}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              nemo obcaecati dolor blanditiis magnam repudiandae in repellendus
              voluptates quibusdam natus?
            </p>
          </div>
        </div>
      </section>
      <section id='exam' className={styles.exam}>
        <div className={styles.contentContainer}>
          <img
            className={styles.sectionImg}
            src={examImg}
            alt='SVG-изображение'
          />
          <div className={styles.textContainer}>
            <h2 className={styles.themeTitle}>Экзамен</h2>
            <p className={styles.text}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              nemo obcaecati dolor blanditiis magnam repudiandae in repellendus
              voluptates quibusdam natus?
            </p>
          </div>
        </div>
      </section>
      <section id='about' className={styles.about}>
        <div className={styles.contentContainer}>
          <img
            className={styles.sectionImg}
            src={projectImg}
            alt='SVG-изображение'
          />
          <div className={styles.textContainer}>
            <h2 className={styles.themeTitle}>О проекте</h2>
            <p className={styles.text}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              nemo obcaecati dolor blanditiis magnam repudiandae in repellendus
              voluptates quibusdam natus?
            </p>
          </div>
        </div>
      </section>
      <footer className={styles.footer}>© 2022 MIREA NINJA</footer>
    </>
  );
}
