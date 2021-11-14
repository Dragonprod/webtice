/* eslint-disable no-unused-vars */
import React from "react";
import styles from "../styles/Menu.module.css";
import logo from "../assets/logo.svg";
import { Link } from "react-router-dom";

export default function Menu() {
  return (
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
          <Link className={styles.menuLink} to="/">
            Главная
          </Link>
          <Link
            className={styles.menuLink}
            to={{
              pathname: "https://github.com/maxcore25/FrontEndStudy",
            }}
            target="_blank"
          >
            GitHub
          </Link>
        </div>
      </nav>
    </header>
  );
}
