import {
  Key, ReactChild, ReactFragment, ReactPortal,
} from 'react';
import { DropdownTypes } from '../../types/Dropdowntypes';
import styles from './Dropdown.module.scss';

const Dropdown = ({ open, trigger, menu }:DropdownTypes) => (
  <div className="dropdown">
    {trigger}
    {open ? (
      <ul className={styles.dropdownList}>
        {menu.map((
          menuItem: boolean | ReactChild | ReactFragment | ReactPortal | null | undefined,
          index: Key | null | undefined,
        ) => (
          <li key={index} className={styles.dropdownItem}>{menuItem}</li>
        ))}
      </ul>
    ) : null}
  </div>
);

export default Dropdown;
