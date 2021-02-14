angular.module('components', [])

    .directive('newgame', function () {
      return {
        restrict: 'E',
        transclude: true,
        scope: {},
        templateUrl: 'app/new-game.template.html',
        replace: true,
        controller: function ($scope, $element, $http) {
          $scope.alert = {
            type: 'success',
            show: false,
            messages: [],
            reset: function () {
              this.messages = [];
              this.show = false;
            }
          };

          let onError = function (errorResponse) {
            showErrorMessage(errorResponse.data.error);
          };

          let showErrorMessage = function (errorMessage) {
            $scope.alert.reset();
            $scope.alert.type = 'danger';
            $scope.alert.show = true;
            $scope.alert.messages.push(errorMessage);
          }

          let showSuccessMessage = function (message) {
            $scope.alert.reset();
            $scope.alert.type = 'success';
            $scope.alert.show = true;
            $scope.alert.messages.push(message);
          }

          let showInfoMessage = function (message) {
            $scope.alert.reset();
            $scope.alert.type = 'info';
            $scope.alert.show = true;
            $scope.alert.messages.push(message);
          }

          let symbol = {
            createUnknown: function () {
              return {
                visible: false,
                icon: "far fa-question-circle",
                text: "Unknown"
              }
            },
            createBy: function (symbolId) {
              let symbol = this.createUnknown();

              if (symbolId === 1 || symbolId === "PAPER") {
                symbol.visible = true;
                symbol.icon = 'far fa-copy';
                symbol.text = 'Paper';
              } else if (symbolId === 2 || symbolId === "ROCK") {
                symbol.visible = true;
                symbol.icon = 'fas fa-cube';
                symbol.text = 'Rock';
              } else if (symbolId === 3 || symbolId === "SCISSORS") {
                symbol.visible = true;
                symbol.icon = 'fas fa-cut';
                symbol.text = 'Scissors';
              }

              return symbol;
            }
          }

          $scope.game = {
            gameId: "",
            playerId: ""
          }

          $scope.board = {
            winner: "Unknown",
            isEqual: false,
            player: {
              score: 0,
              symbol: symbol.createUnknown(),
            },
            computer: {
              score: 0,
              symbol: symbol.createUnknown(),
              pending: true
            },
            updateBoard: function (gameResult) {
              let _this = this;

              _this.areYouWinner = gameResult.winnerPlayerId === $scope.game.playerId;
              _this.isEqual = gameResult.equal;

              gameResult.players.forEach(function (player) {
                if (player.id === $scope.game.playerId) {
                  _this.player.score = player.score;
                  _this.player.symbol = symbol.createBy(player.symbol);
                } else {
                  _this.computer.score = player.score;
                  _this.computer.symbol = symbol.createBy(player.symbol);
                  _this.computer.pending = false;
                }
              });

              if (_this.isEqual) {
                showInfoMessage("TIE!");
                this.setUnknownWinner();
              } else if (gameResult.winnerPlayerId === $scope.game.playerId) {
                this.winner = "player";
                showSuccessMessage("YOU WIN!")
              } else {
                this.winner = "computer";
                showErrorMessage("YOU LOSE!")
              }
            },
            setUnknownWinner: function () {
              this.winner = "Unknown";
            },
            setPlayerSymbol: function (symbolId) {
              this.player.symbol = symbol.createBy(symbolId);
            },
            reset: function () {
              this.setUnknownWinner();
              this.player.symbol = symbol.createUnknown();
              this.computer.symbol = symbol.createUnknown();
              this.computer.symbol.visible = true;
              this.computer.pending = true;
            }
          }

          let createNewGame = function () {
            $http.post('/games/create/singlePlayer')
                .then(function onSuccess(response) {
                  $scope.board.reset();
                  const createGameResponse = response.data;
                  $scope.game.gameId = createGameResponse.gameId;
                  $scope.game.playerId = createGameResponse.playerId;
                }, onError);
          }

          $scope.getComputerChoseClass = function () {
            const winner = $scope.board.winner;
            if (winner === "computer") {
              return "btn-success";
            } else if (winner === "player") {
              return "btn-danger";
            } else {
              return "btn-info";
            }
          }

          $scope.getPlayerChoseClass = function () {
            const winner = $scope.board.winner;
            if (winner === "player") {
              return "btn-success";
            } else if (winner === "computer") {
              return "btn-danger";
            } else {
              return "btn-info";
            }
          }

          $scope.play = function (symbolId) {
            let gameId = $scope.game.gameId;
            let playerId = $scope.game.playerId;

            let playResourceUrl = `/games/${gameId}/${playerId}/play/${symbolId}`;

            $scope.board.setPlayerSymbol(symbolId);
            $http.put(playResourceUrl)
                .then(function onSuccess(response) {
                  $scope.alert.reset();
                  const gameResult = response.data;

                  $scope.board.reset();
                  $scope.board.updateBoard(gameResult);
                }, onError);
          };

          $scope.rematch = function () {
            $scope.alert.reset();
            $scope.board.reset();
          }

          createNewGame();
        }
      };
    });