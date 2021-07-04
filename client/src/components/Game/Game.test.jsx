import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import configureStore from "redux-mock-store";
import { Provider } from "react-redux";

import Game from "./Game";

const mockStore = configureStore([]);
let store;

const { getComputedStyle } = window;
window.getComputedStyle = (elt) => getComputedStyle(elt);

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
  store = mockStore({
    game: {
      id: 1,
      createAt: "2021-06-25T02:57:06.537+00:00",
      pitsNumber: 7,
      ballsNumber: 4,
      player1: {
        id: 1,
        user: {
          id: 1,
          email: "user1@test.com",
        },
        pits: [4, 4, 4, 0, 4, 3, 4],
        hut: 5,
      },
      player2: {
        id: 2,
        user: {
          id: 2,
          email: "user2@test.com",
        },
        pits: [4, 4, 4, 4, 0, 4, 4],
        hut: 4,
      },
      status: "IN_PROGRESS",
      winner: null,
      turn: {
        id: 1,
        email: "user1@test.com",
      },
    },
  });
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("render IN_PROGRESS game", () => {
  act(() => {
    render(
      <Provider store={store}>
        <Game />
      </Provider>,
      container
    );
  });
  expect(container.textContent).toBe(
    "user1@test.com5434044444440444user2@test.com"
  );

  const pits = container.querySelectorAll(".pits");
  expect(pits.length).toBe(2);

  const internalPitsPlayer1 = pits[0].querySelectorAll(".pit");
  expect(internalPitsPlayer1.length).toBe(7);

  const internalPitsPlayer2 = pits[1].querySelectorAll(".pit");
  expect(internalPitsPlayer2.length).toBe(7);

  const huts = container.querySelectorAll(".hut");
  expect(huts.length).toBe(2);
  expect(huts[0].textContent).toBe("5");
  expect(huts[1].textContent).toBe("4");

  const users = container.querySelectorAll(".user");
  expect(users.length).toBe(2);

  const userActives = container.querySelectorAll(".user.active");
  expect(userActives.length).toBe(1);
});

it("render FINISHED game with a winner", () => {
  store = mockStore({
    game: {
      id: 1,
      createAt: "2021-06-25T02:57:06.537+00:00",
      pitsNumber: 7,
      ballsNumber: 4,
      player1: {
        id: 1,
        user: {
          id: 1,
          email: "user1@test.com",
        },
        pits: [4, 4, 4, 0, 4, 3, 4],
        hut: 5,
      },
      player2: {
        id: 2,
        user: {
          id: 2,
          email: "user2@test.com",
        },
        pits: [4, 4, 4, 4, 0, 4, 4],
        hut: 4,
      },
      status: "FINISHED",
      winner: {
        id: 1,
        email: "user1@test.com",
      },
      turn: {
        id: 1,
        email: "user1@test.com",
      },
    },
  });
  act(() => {
    render(
      <Provider store={store}>
        <Game />
      </Provider>,
      container
    );
  });

  const userWinner = container.querySelectorAll(".user.winner");
  expect(userWinner.length).toBe(1);
});

it("render FINISHED game with no winner", () => {
  store = mockStore({
    game: {
      id: 1,
      createAt: "2021-06-25T02:57:06.537+00:00",
      pitsNumber: 7,
      ballsNumber: 4,
      player1: {
        id: 1,
        user: {
          id: 1,
          email: "user1@test.com",
        },
        pits: [4, 4, 4, 0, 4, 3, 4],
        hut: 5,
      },
      player2: {
        id: 2,
        user: {
          id: 2,
          email: "user2@test.com",
        },
        pits: [4, 4, 4, 4, 0, 4, 4],
        hut: 4,
      },
      status: "FINISHED",
      winner: null,
      turn: {
        id: 1,
        email: "user1@test.com",
      },
    },
  });
  act(() => {
    render(
      <Provider store={store}>
        <Game />
      </Provider>,
      container
    );
  });

  const userWinner = container.querySelectorAll(".user.winner");
  expect(userWinner.length).toBe(2);
});
