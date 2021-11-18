/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import Menu from "../components/ReferenceMenu";
import styles from "../styles//ReferenceBookPage.module.css";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Autocomplete from "@mui/material/Autocomplete";
import API from "../api/api";
import TagPage from "../components/TagPage";
import CodeEditor from "../components/CodeEditor";
import Skeleton from "@mui/material/Skeleton";


import ListItemButton from '@mui/material/ListItemButton';

import Collapse from '@mui/material/Collapse';

import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';


const ReferenceBook = () => {
  const [htmlTags, sethtmlTags] = useState([]);
  const [cssTags, setcssTags] = useState([]);
  const [loading, setLoading] = useState(true);
  const [tagPage, settagPage] = useState();
  //const [open, setOpen] = useState(true);

  useEffect(() => {
    const getTagsData = async () => {
      const htmlResponse = await API.get("/tag");
      sethtmlTags(htmlResponse.data);
      settagPage(
        <TagPage
          name={htmlResponse.data[0].name}
          description={htmlResponse.data[0].description}
          attrs={htmlResponse.data[0].attributes}
        />
      );

      const cssResponse = await API.get("/style");
      setcssTags(cssResponse.data);

      setLoading(false);
    };

    getTagsData();
  }, []); 

  // const handleClick = () => {
  //   setOpen(!open);
  // };

  const handleItemChange = (tag) => {
    settagPage(
      <TagPage
        name={tag.name}
        description={tag.description}
        attrs={tag.attributes}
      />
    );
  };

  const htmlTagsData = htmlTags.map((htmlTag) => (
    <ListItem
      key={htmlTag.id}
      onClick={() => {
        handleItemChange(htmlTag);
      }}
    >
      <ListItemText primary={htmlTag.name} />
    </ListItem>
  ));

  const cssTagsData = cssTags.map((cssTag) => (
    <ListItem
      key={cssTag.id}
      // onClick={() => {
      //   handleItemChange(cssTag);
      // }}
    >
      <ListItemText primary={cssTag.styleName} />
    </ListItem>
  ));

  return (
    <>
      <Menu />
      {loading && (
        <>
          <div className={styles.mainGrid}>
            <div className={styles.sidebar}>
              <Skeleton
                variant="rectangular"
                height={40}
                sx={{ bgcolor: "rgba(255,255,255,.1)", borderRadius: "20px" }}
              />
              <Skeleton
                variant="text"
                height={60}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  marginTop: "10px",
                  width: "70%",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />

              <Skeleton
                variant="text"
                height={60}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  marginTop: "10px",
                  width: "70%",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  margin: "-4px 0 0 30px",
                }}
              />
            </div>
            <div className={styles.mainСontent}>
              <Skeleton
                variant="text"
                width={300}
                height={70}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "10px",
                  marginTop: "-13px",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "5px",
                  margin: "-8px 0",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "5px",
                  margin: "-8px 0",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "5px",
                  margin: "-8px 0",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "5px",
                  margin: "-8px 0",
                }}
              />
              <Skeleton
                variant="text"
                height={40}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "5px",
                  margin: "-8px 0",
                }}
              />
              <Skeleton
                variant="rectangular"
                height={500}
                sx={{
                  bgcolor: "rgba(255,255,255,.1)",
                  borderRadius: "20px",
                  marginTop: "40px",
                }}
              />
            </div>
          </div>
        </>
      )}
      {!loading && (
        <div className={styles.mainGrid}>
          <div className={styles.sidebar}>
            <Stack spacing={2} sx={{ width: 240 }}>
              <Autocomplete
                className={styles.searchbar}
                id="search-main"
                freeSolo
                options={htmlTags.map((option) => option.name)}
                renderInput={(params) => (
                  <TextField {...params} label="Поиск" />
                )}
              />
            </Stack>
            {/* <ListItemButton onClick={handleClick}>
              <ListItemText primary="HTML" />
              {open ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <List component="div" disablePadding>
                <React.Fragment>
                  <List>{htmlTagsData}</List>
                </React.Fragment>
              </List>
            </Collapse> */}
            <h2 className={styles.htmlTitle}>HTML</h2>
            <nav className={styles.sidebarNavLinks} aria-label="html tags">
              <React.Fragment>
                <List>{htmlTagsData}</List>
              </React.Fragment>
            </nav>
            <h2 className={styles.cssTitle}>CSS</h2>
            <nav className={styles.sidebarNavLinks} aria-label="css tags">
              <React.Fragment>
                <List>{cssTagsData}</List>
              </React.Fragment>
            </nav>
          </div>
          <div className={styles.mainСontent}>
            {tagPage}
            {/* <CodeEditor /> */}
          </div>
        </div>
      )}
    </>
  );
};
export default ReferenceBook;
