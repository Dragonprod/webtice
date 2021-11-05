import React from "react";
import styles from "../styles/Menu.module.css";
import logo from "../assets/logo.svg";
import { BrowserRouter as Router, Link } from "react-router-dom";

export default function Menu() {
  return (
    <Router>
      <header className={styles.headerBackground}>
        <Link className={styles.navLogoRef} to="/">
          <img className={styles.menuLogo} src={logo} alt="Логотип" />
          <span className={styles.logoName}>Webtice</span>
        </Link>
        <input type="checkbox" name="menu" id={styles.check} />
        <div id="menuBtn" className={styles.menuBtn}>
          <div className={styles.menuBtnBurger}></div>
        </div>
        <nav className={styles.menuNav}>
          <div className={styles.menuNavLinks}>
            <a
              href="#LandingPage_hero__2HwsX"
              className={styles.landingMenuLink}
            >
              Главная
            </a>
            <a
              href="#LandingPage_handbook__3n8zN"
              className={styles.landingMenuLink}
            >
              Веб-справочник
            </a>
            <a
              href="#LandingPage_coding__6cKMM"
              className={styles.landingMenuLink}
            >
              Онлайн кодинг
            </a>
            <a
              href="#LandingPage_exam__1pBj6"
              className={styles.landingMenuLink}
            >
              Экзамен
            </a>
            <a
              href="#LandingPage_about__132uQ"
              className={styles.landingMenuLink}
            >
              О проекте
            </a>
          </div>
        </nav>
      </header>
    </Router>
  );
}
