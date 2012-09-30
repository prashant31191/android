package eu.ttbox.geoping.domain.pairing;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.CheckBox;
import android.widget.TextView;
import eu.ttbox.geoping.domain.model.Pairing;
import eu.ttbox.geoping.domain.model.PairingAuthorizeTypeEnum;
import eu.ttbox.geoping.domain.pairing.PairingDatabase.PairingColumns;

public class PairingHelper {

    boolean isNotInit = true;
    public int idIdx = -1;
    public int nameIdx = -1;
    public int phoneIdx = -1;
    public int phoneNormalizedIdx = -1;
    public int authorizeTypeIdx = -1;
    public int showNotificationIdx = -1;

    public PairingHelper initWrapper(Cursor cursor) {
        idIdx = cursor.getColumnIndex(PairingColumns.COL_ID);
        nameIdx = cursor.getColumnIndex(PairingColumns.COL_NAME);
        phoneIdx = cursor.getColumnIndex(PairingColumns.COL_PHONE);
        phoneNormalizedIdx = cursor.getColumnIndex(PairingColumns.COL_PHONE_NORMALIZED);
        authorizeTypeIdx = cursor.getColumnIndex(PairingColumns.COL_AUTHORIZE_TYPE);
        showNotificationIdx = cursor.getColumnIndex(PairingColumns.COL_SHOW_NOTIF);
        isNotInit = false;
        return this;
    }

    public Pairing getEntity(Cursor cursor) {
        if (isNotInit) {
            initWrapper(cursor);
        }
        Pairing user = new Pairing();
        user.setId(idIdx > -1 ? cursor.getLong(idIdx) : -1);
        user.setName(nameIdx > -1 ? cursor.getString(nameIdx) : null);
        user.setPhone(phoneIdx > -1 ? cursor.getString(phoneIdx) : null);
        user.setAuthorizeType(authorizeTypeIdx > -1 ? PairingAuthorizeTypeEnum.getByCode(cursor.getInt(authorizeTypeIdx)) : null);
        user.setShowNotification(showNotificationIdx > -1 ? cursor.getInt(showNotificationIdx) == 1 ? true : false : false);
        return user;
    }

    private PairingHelper setTextWithIdx(TextView view, Cursor cursor, int idx) {
        view.setText(cursor.getString(idx));
        return this;
    }

    public PairingHelper setTextPairingId(TextView view, Cursor cursor) {
        return setTextWithIdx(view, cursor, idIdx);
    }

    public String getPairingIdAsString(Cursor cursor) {
        return cursor.getString(idIdx);
    }

    public long getPairingId(Cursor cursor) {
        return cursor.getLong(idIdx);
    }

    public int getPairingColor(Cursor cursor) {
        return cursor.getInt(authorizeTypeIdx);
    }

    public PairingHelper setTextPairingName(TextView view, Cursor cursor) {
        return setTextWithIdx(view, cursor, nameIdx);
    }

    public PairingHelper setTextPairingPhone(TextView view, Cursor cursor) {
        return setTextWithIdx(view, cursor, phoneIdx);
    }

    public PairingHelper setCheckBoxPairingShowNotif(CheckBox view, Cursor cursor) {
        boolean showNotif = cursor.getInt(showNotificationIdx) == 1;
        view.setChecked(showNotif);
        return this;
    }

    public String getPairingPhone(Cursor cursor) {
        return cursor.getString(phoneIdx);
    }

    public static ContentValues getContentValues(Pairing user) {
        ContentValues initialValues = new ContentValues();
        if (user.id > -1) {
            initialValues.put(PairingColumns.COL_ID, Long.valueOf(user.id));
        }
        initialValues.put(PairingColumns.COL_NAME, user.name);
        initialValues.put(PairingColumns.COL_PHONE, user.phone);
        initialValues.put(PairingColumns.COL_SHOW_NOTIF, user.showNotification);
        // secu
        PairingAuthorizeTypeEnum authorizeType = user.authorizeType != null ? user.authorizeType : PairingAuthorizeTypeEnum.AUTHORIZE_REQUEST;
        initialValues.put(PairingColumns.COL_AUTHORIZE_TYPE, authorizeType.getCode());
        return initialValues;
    }

}
