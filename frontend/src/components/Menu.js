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
          {/* <span className={styles.logoName}>Webtice</span> */}
        </Link>
        <input type="checkbox" name="menu" id={styles.check} />
        <div id="menuBtn" className={styles.menuBtn}>
          <div className={styles.menuBtnBurger}></div>
        </div>
        <nav className={styles.menuNav}>
          <div className={styles.menuNavLinks}>
            <a href="#App_hero__2xCMo" className={styles.landingMenuLink}>
              Главная
            </a>
            <a href="#App_about__2tjJy" className={styles.landingMenuLink}>
              О проекте
            </a>
            <a href="#App_handbook__2-Sp1" className={styles.landingMenuLink}>
              Веб-справочник
            </a>
            <a href="#App_coding__1zK4z" className={styles.landingMenuLink}>
              Онлайн кодинг
            </a>
            <a href="#App_exam__2Y0p6" className={styles.landingMenuLink}>
              Экзамен
            </a>
          </div>
        </nav>
      </header>
    </Router>
  );
}
