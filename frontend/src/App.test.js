import { render, screen } from "@testing-library/react";
import LandingPage from "./pages/LangingPage";

test("renders main title part one", () => {
  render(<LandingPage />);

  const titlePartOne = screen.getByText(/Начни учиться прямо/i);
  expect(titlePartOne).toBeInTheDocument();
});

test("renders main title part two", () => {
  render(<LandingPage />);

  const titlePartTwo = screen.getByText(/сейчас/i);
  expect(titlePartTwo).toBeInTheDocument();
});

test("renders description", () => {
  render(<LandingPage />);
  const linkElement = screen.getByText(
    /Webtice — образовательная платформа РТУ МИРЭА для обучения студентов веб-разработке и проведения экзаменов, преимущественно по компьютерным дисциплинам/i
  );
  expect(linkElement).toBeInTheDocument();
});

// test('renders card buttons text', () => {
//   render(<App />);
//   const firstText = screen.getByText(/Веб-справочник/i);
//   expect(firstText).toBeInTheDocument();

//   const secondText = screen.getByText(/Онлайн Кодинг/i);
//   expect(secondText).toBeInTheDocument();

//   const thirdText = screen.getByText(/Экзамен/i);
//   expect(thirdText).toBeInTheDocument();
// });
