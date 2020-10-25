const { DataTypes } = require('sequelize');

module.exports = model;

function model(sequelize) {
    const attributes = {
        user_id: {
            type: DataTypes.INTEGER,
            references: {
              model: 'Users',
              key: 'id',
            }
        },
    
        type: { type: DataTypes.STRING, allowNull: false },
        serialNumber: { type: DataTypes.STRING, allowNull: true }
    };

    return sequelize.define('Meter', attributes);
}