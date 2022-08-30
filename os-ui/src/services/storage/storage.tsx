class Storage {
/* eslint-disable class-methods-use-this */
  addItemToLocalStorage(key:string, value:string) {
    if (typeof value === 'object') {
      localStorage.setItem(key, JSON.stringify(value));
    } else {
      localStorage.setItem(key, value);
    }
  }

  removeItemFromLocalStorage(key:string) {
    localStorage.removeItem(key);
  }

  getItemFromLocalStorage(key:string) {
    if (localStorage.getItem(key)) {
      return JSON.parse(localStorage.getItem(key) as string);
    }
  }
}
export const storage = new Storage();
