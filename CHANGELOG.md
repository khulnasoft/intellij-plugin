<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Khulnasoft IntelliJ Plugin Changelog

## Unreleased

## 0.2.0 - 2023-04-04
- Support for GoLand 2023.1
- Add support for annotating Khulnasoft API directives, with some basic validation

## 0.1.2 - 2022-12-09

### Added
- Support for GoLand 2022.3

## 0.1.1

### Bugs
- Fixes #8 - Null pointer exception under some conditions with the sqldb detector logic.
- Fixes a null pointer exception when there is no `defaultModule` for a project.

## 0.1.0

### Added
- From Khulnasoft v1.9.3 services databases can be automatically detected and configured within the IDE.
  This will add both the local database used by `khulnasoft run` and the test database used by `khulnasoft test`
- New settings panel which allows the plugin to be configured to use a different khulnasoft binary (useful when testing features)

## 0.0.3

### Bugs
- Fix support for directory tests (previously only file and function level tests worked).
- Changed the `newapi` live template to fix a typo and renamed the parameters prop to `p`

## 0.0.2

### Added
- Added support for running unit tests of Khulnasoft applications in GoLand
- Added a live template `newapi` for creating a new API easily within GoLand
- Add syntax highlighting and icon for `khulnasoft.app` files
