PRAGMA foreign_keys = ON;

CREATE TABLE Movies
(
    id           INTEGER PRIMARY KEY,
    title        TEXT NOT NULL,
    overview     TEXT NULL,
    posterPath   TEXT NULL,
    backdropPath TEXT NULL,
    voteAverage  REAL NOT NULL
);

CREATE TABLE PopularMoviesList
(
    position INTEGER PRIMARY KEY CHECK ( position >= 0 ),
    movieId  INTEGER NOT NULL REFERENCES Movies
);

CREATE TABLE Genres
(
    id   INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE MovieGenres
(
    movieId INTEGER NOT NULL REFERENCES Movies,
    genreId INTEGER NOT NULL REFERENCES Genres,
    PRIMARY KEY (movieId, genreId)
);

CREATE TABLE ProductionCompanies
(
    id       INTEGER PRIMARY KEY,
    name     TEXT NOT NULL,
    logoPath TEXT NULL
);

CREATE TABLE MovieProductionCompanies
(
    movieId             INTEGER NOT NULL REFERENCES Movies,
    productionCompanyId INTEGER NOT NULL REFERENCES ProductionCompanies,
    PRIMARY KEY (movieId, productionCompanyId)
);

CREATE TABLE Reviews
(
    id      TEXT PRIMARY KEY,
    author  TEXT    NOT NULL,
    content TEXT    NOT NULL,
    movieId INTEGER NOT NULL REFERENCES Movies
);

-- Create indices for all foreign keys. This is for performance reasons.
CREATE INDEX MovieGenresMovieIdIndex ON MovieGenres (movieId);
CREATE INDEX MovieGenresGenreIdIndex ON MovieGenres (genreId);
CREATE INDEX MovieProductionCompaniesMovieIdIndex ON MovieProductionCompanies (movieId);
CREATE INDEX MovieProductionCompaniesIdIndex ON MovieProductionCompanies (productionCompanyId);
CREATE INDEX ReviewsMovieIdIndex ON Reviews (movieId);
CREATE INDEX PopularMoviesListMovieIdIndex ON PopularMoviesList (movieId);

CREATE VIEW PopularMovies AS
SELECT Movies.*, PML.position
FROM Movies
         JOIN PopularMoviesList PML ON Movies.id = PML.movieId;

CREATE VIEW GenresByMovie AS
SELECT Genres.*, MG.movieId
FROM Genres
         JOIN MovieGenres MG ON Genres.id = MG.genreId;

CREATE VIEW ProductionCompaniesByMovie AS
SELECT ProductionCompanies.*, MPC.movieId
FROM ProductionCompanies
         JOIN MovieProductionCompanies MPC ON ProductionCompanies.id = MPC.productionCompanyId;
