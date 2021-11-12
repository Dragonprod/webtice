/* eslint-disable no-unused-vars */
import React, { useState, useEffect} from "react";
import styles from "../styles/Menu.module.css";
import styles2 from "../styles/Tags.module.css";
import logo from "../assets/logo.svg";
import { Link } from "react-router-dom";

import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Autocomplete from "@mui/material/Autocomplete";

import { tagsAndProperties } from "../pages/ReferenceBookPage.js";
import API from "../api/api";
import { renderTagsList } from "../services/renders";

export default function ReferenceBookMenu() {
  const [tags, setTags] = useState([]);

  useEffect(() => {
    const getTagsApi = async () => {
      const response = await API.get("/tag");
      setTags(response.data);
    };
    getTagsApi();
  }, []);

  const tagsData = renderTagsList(tags);

  return (
    <>
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
          {/* ============ Upper Menu Links ============*/}
          <div className={styles.menuNavLinks}>
            <a href="/" className={styles.menuLink}>
              Главная
            </a>
            <a
              href="https://github.com/maxcore25/FrontEndStudy"
              className={styles.menuLink}
            >
              GitHub
            </a>
          </div>

          {/* ============ Side Bar Nav Links ============*/}
          <div className={styles2.sidebar}>
            <Stack spacing={2} sx={{ width: 240 }}>
              <Autocomplete
                className={styles2.searchbar}
                id="search-main"
                freeSolo
                options={tags.map((option) => option.name)}
                renderInput={(params) => (
                  <TextField {...params} label="Поиск" />
                )}
              />
            </Stack>
            <h2 className={styles2.htmlTitle}>HTML</h2>
            <nav
              className={styles2.sidebarNavLinks}
              aria-label="secondary mailbox folders"
            >
              <List>{tagsData}</List>
            </nav>
            <h2 className={styles2.cssTitle}>CSS</h2>
            <nav
              className={styles2.sidebarNavLinks}
              aria-label="secondary mailbox folders"
            >
              <List>
                <ListItem disablePadding>
                  <ListItemButton component="a" href="#align-content">
                    <ListItemText primary="align-content" />
                  </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                  <ListItemButton component="a" href="#align-items">
                    <ListItemText primary="align-items" />
                  </ListItemButton>
                </ListItem>
              </List>
            </nav>
          </div>
        </nav>
      </header>
    </>
  );
}
