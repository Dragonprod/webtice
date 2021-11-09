/* eslint-disable no-unused-vars */
import React, { PureComponent } from "react";
import styles from "../styles//ReferenceBookPage.module.css";

class TagPage extends PureComponent {
  render() {
    const { name, description, attrs } = this.props;

    let desc = description.length > 0 ? description : "Описание отсутствует";

    return (
      <div className={styles.textContainer}>
        <h2 className={styles.themeTitle}>{name}</h2>
        <p className={styles.text}>{desc}</p>
      </div>
    );
  }
}

export default TagPage;
